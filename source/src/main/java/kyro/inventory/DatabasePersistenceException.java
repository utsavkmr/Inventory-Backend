package kyro.inventory;

/**
 * Database persistence exception
 *
 * @author fahrur
 * @version 1.0
 */
public class DatabasePersistenceException extends Exception {

    /**
     * Constructor with message parameter.
     *
     * @param message
     *            the message
     */
    public DatabasePersistenceException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause parameters.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public DatabasePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
