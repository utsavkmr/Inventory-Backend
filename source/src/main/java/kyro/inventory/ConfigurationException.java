package kyro.inventory;

/**
 * Configuration exception
 *
 * @author fahrur
 * @version 1.0
 */
public class ConfigurationException extends RuntimeException {

    /**
     * Constructor with message parameter.
     *
     * @param message
     *            the message
     */
    public ConfigurationException(String message) {
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
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
