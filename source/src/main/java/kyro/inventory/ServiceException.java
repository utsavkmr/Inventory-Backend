package kyro.inventory;

/**
 * Service exception
 *
 * @author fahrur
 * @version 1.0
 */
public class ServiceException extends Exception {

    /**
     * Constructor with message parameter.
     *
     * @param message
     *            the message
     */
    public ServiceException(String message) {
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
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
