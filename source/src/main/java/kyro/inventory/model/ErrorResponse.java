package kyro.inventory.model;

/**
 * ErrorResponse
 *
 * @author fahrur
 * @version 1.0
 */
public class ErrorResponse {
    /**
     * Error code
     */
    private int errorCode;

    /**
     * Message
     */
    private String message;

    /**
     * Get the error code
     * @return the error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Set the error code
     * @param errorCode the error code
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Get the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
