package kyro.inventory.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kyro.inventory.model.ActiveAuditableEntity;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.Set;
import java.lang.reflect.Field;

/**
 * Created by fahrur on 11/21/2016.
 */
public class ServiceHelper {

    /**
     * Jackson {@link ObjectMapper} instance.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * <p>
     * Represents the error message.
     * </p>
     */
    private static final String MESSAGE_ERROR = "Error in method %1$s. Details:";

    /**
     * <p>
     * Represents the action message.
     * </p>
     */
    private static final String MESSAGE_ACTION = "Action in method %1$s. Details:%2$s";


    public static <T> String entityErrorMessageHelper(Set<ConstraintViolation<T>> constraintViolations){
        String errorMessage = "";
        if(constraintViolations!=null){
            for(ConstraintViolation<T> constraintViolation : constraintViolations){
                errorMessage += constraintViolation.getPropertyPath()+" "+constraintViolation.getMessage()+"\n";
            }
        }
        return errorMessage;
    }

    /**
     * This helper method converts model instance to its JSON representation.
     *
     * @param obj
     *            Instance of model.
     * @return JSON representation.
     */
    public static final String toString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            return "cannot get the string express";
        }
    }

    /**
     * Logs action.
     *
     * @param logger
     *            the logger
     * @param signature
     *            the signature
     * @param msg
     *            the message to log
     */
    public static void logAction(Logger logger, String signature, String msg) {
        if (null != logger && logger.isInfoEnabled()) {
            // Do logging
            logger.info(String.format(MESSAGE_ACTION, signature, msg));
        }
    }

    /**
     * <p>
     * Checks whether value is null/empty.
     * </p>
     *
     * @param value
     *            the string value
     * @return the flag whether value is null/empty
     */
    static boolean isStringNullOrEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    /**
     * <p>
     * Validates the a variable. The value can not be <code>null</code>.
     * </p>
     *
     * @param logger
     *            the logger object.
     * @param signature
     *            the signature of the method to be logged.
     * @param value
     *            the the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     * @throws IllegalArgumentException
     *             if the the variable is <code>null</code>.
     */
    public static void checkNull(Logger logger, String signature, Object value, String name) {
        if (value == null) {
            // Log exception
            throw logException(logger, signature, new IllegalArgumentException("'" + name + "' should not be null."));
        }
    }

    /**
     * Checks if the given <code>value</code> is positive or not.
     *
     * @param logger
     *            the logger
     * @param signature
     *            the signature calling this method
     * @param value
     *            Value to check.
     * @param name
     *            Name of the variable.
     * @throws IllegalArgumentException
     *             if the value is not positive.
     */
    public static void checkPositive(Logger logger, String signature, long value, String name) {
        if (value <= 0) {
            throw logException(logger, signature, new IllegalArgumentException(name + " should be positive."));
        }
    }

    /**
     * Checks if the given <code>value</code> is not negative.
     *
     * @param logger
     *            the logger
     * @param signature
     *            the signature calling this method
     * @param value
     *            Value to check.
     * @param name
     *            Name of the variable.
     * @throws IllegalArgumentException
     *             if the value is negative.
     */
    private static void checkNotNegative(Logger logger, String signature, long value, String name) {
        if (value < 0) {
            throw logException(logger, signature, new IllegalArgumentException(name + " should not be negative."));
        }
    }

    /**
     * This method validates given long array is not null/empty, does not contains non-positive value.
     *
     * @param logger
     *            the logger
     * @param signature
     *            the signature calling this method
     * @param longArray
     *            long array to check.
     * @param name
     *            Name of the variable.
     * @throws IllegalArgumentException
     *             If given long array is null/empty, contains non-positive value.
     */
    public static void checkLongArray(Logger logger, String signature, long[] longArray, String name) {
        checkNull(logger, signature, longArray, name);
        if (longArray.length == 0) {
            throw logException(logger, signature, new IllegalArgumentException(name + " should not be empty."));
        }
        for (long value : longArray) {
            checkPositive(logger, signature, value, "value of " + name);
        }
    }

    /**
     * <p>
     * Validates the a variable. The value can not be <code>null/empty</code>.
     * </p>
     *
     * @param logger
     *            the logger object.
     * @param signature
     *            the signature of the method to be logged.
     * @param value
     *            the the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     * @throws IllegalArgumentException
     *             if the the variable is <code>null/empty</code>.
     */
    public static void checkStrNullEmpty(Logger logger, String signature, String value, String name) {
        if (isStringNullOrEmpty(value)) {
            // Log exception
            throw logException(logger, signature, new IllegalArgumentException("'" + name
                    + "' should not be null/empty."));
        }
    }

    /**
     * <p>
     * Logs the given exception and message at <code>ERROR</code> level.
     * </p>
     *
     * @param <T>
     *            the exception type.
     * @param logger
     *            the logger object).
     * @param signature
     *            the signature of the method to log.
     * @param e
     *            the exception to log.
     * @return the passed in exception.
     */
    public static <T extends Throwable> T logException(Logger logger, String signature, T e) {
        if (null != logger && logger.isDebugEnabled()) {
            String errorMessage = String.format(MESSAGE_ERROR, signature);

            // Do logging
            logger.error(errorMessage, e);
        }
        return e;
    }


    /**
     * This method determines if the given column is contained in specified type's fields.
     *
     * @param column
     *            Name of the column
     * @param type
     *            the entity type
     * @return true if contained; false otherwise.
     */
    private static boolean containsColumn(String column, Class<?> type) {
        if (column.contains(".")) {
            // JPA does not support complex sorting scheme.
            return false;
        } else {
            while (null != type) {
                Field[] fields = type.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().equalsIgnoreCase(column)) {
                        return true;
                    }
                }
                type = type.getSuperclass();
            }
        }
        return false;
    }

    public static <T> String getClassNameForSearch(Class<T> type) {
        return type.getName();
    }

    public static String getSearchLikeString(String value){
        return "%" + value + "%";
    }


}
