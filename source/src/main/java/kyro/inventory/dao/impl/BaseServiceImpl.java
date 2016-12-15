package kyro.inventory.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import static kyro.inventory.dao.impl.ServiceHelper.entityErrorMessageHelper;

/**
 * Created by fahrur on 11/21/2016.
 */
@Transactional
public abstract class BaseServiceImpl<T extends IdentifiableEntity> {

    /**
     * Represents the Java Class object for the entity's class.
     *
     * Non-null.
     *
     * It will be initialized to Class<T> in constructor and will not change afterwards.
     */
    private final Class<T> entityClass;

    /**
     * Represents the EntityManager used to access database persistence.
     *
     * Required. Not null.
     */
    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Validator
     */
    @Autowired
    protected Validator validator;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    protected MessageByLocaleServiceImpl messageService;

    /**
     * The class name of the class extends this class.
     */
    protected final String CLASS_NAME = getClass().getName();

    /**
     * Represents the Logger used to perform logging.
     *
     * Optional. If it is not configured, then no logging will be done in the service.
     */
    private Logger logger;

    /**
     * Constructor.
     *
     * @param entityClass
     *            the entity class
     * @throws IllegalArgumentException
     *             if entityClass is null.
     */
    protected BaseServiceImpl(Class<T> entityClass) {
        ServiceHelper.checkNull(getLogger(), CLASS_NAME + ".BaseGenericService(Class<T> entityClass)",
                entityClass, "entityClass");
        this.entityClass = entityClass;
    }

    /**
     * Returns the entityManager.
     *
     * @return the entityManager
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Returns the logger.
     *
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }

    /**
     * Sets the logger.
     *
     * @param logger
     *            the logger to set
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public abstract T create(T entity) throws ServiceException, DatabasePersistenceException;

    public abstract T update(T entity) throws ServiceException, DatabasePersistenceException;

    public <T extends AuditableEntity> T genericAuditableCreate(T entity) throws ServiceException, DatabasePersistenceException {
        setCreatedByAndDate(entity);

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if(constraintViolations!=null && constraintViolations.size()==0){
            entityManager.persist(entity);
        }
        else {
            throw new DatabasePersistenceException(entityErrorMessageHelper(constraintViolations));
        }
        return entity;
    }

    public <T extends AuditableEntity> T genericAuditableUpdate(T entity) throws ServiceException, DatabasePersistenceException {
        setUpdatedByAndDate(entity);

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if(constraintViolations!=null && constraintViolations.size()==0){
            entityManager.merge(entity);
        }
        else {
            throw new DatabasePersistenceException(entityErrorMessageHelper(constraintViolations));
        }
        return entity;
    }

    public T get(Long id) throws ServiceException, DatabasePersistenceException {
        T obj = entityManager.find(entityClass,id);
        return obj;
    }

    public List<T> getAll(Boolean isActive) throws ServiceException, DatabasePersistenceException {
        final String signature = CLASS_NAME + ".search(S criteria)";

        String className = ServiceHelper.getClassNameForSearch(entityClass);

        try {
            StringBuffer sb = new StringBuffer("SELECT e FROM ").append(className).append(" e ");
            // Generate and append WHERE clause
            StringBuffer sbWhere = new StringBuffer("WHERE (1=1)");
            sbWhere.append(" AND e.isActive = true");
            if(isActive) {
                sb.append(sbWhere);
            }
            // Append ORDER BY clause
            sb.append(" ORDER BY ").append("id").append(" ").append("asc");

            // Create query
            TypedQuery<T> query = getEntityManager().createQuery(sb.toString(), entityClass);

            // Execute the query
            List<T> entities = query.getResultList();
            return entities;
        } catch (IllegalStateException e) {
            throw ServiceHelper.logException(getLogger(), signature, new ServiceException(
                    "Entity manager is closed when searching entities", e));
        }
    }

    public static <T extends AuditableEntity> void setCreatedByAndDate(T entity) {
        String username = LoginUserThread.loginUser.get().getUsername();
        entity.setCreatedBy(username);
        entity.setCreatedDate(new Date());
    }

    public static <T extends AuditableEntity> void setUpdatedByAndDate(T entity) {
        String username = LoginUserThread.loginUser.get().getUsername();
        entity.setUpdatedBy(username);
        entity.setUpdatedDate(new Date());
    }

    protected Long getTotalPage(Long totalRow, BaseSearchParameters criteria) {
        long totalPage = 0;

        long limit = criteria.limit;
        totalPage = totalRow / limit;
        totalPage += totalRow % limit > 0 ? 1 : 0;
        return totalPage;
    }

    public void getErrorMessageFromList(List<String> errorMessages,StringBuilder stringBuilder) {
        for(String error : errorMessages) {
            stringBuilder.append(error+", ");
        }
    }

}
