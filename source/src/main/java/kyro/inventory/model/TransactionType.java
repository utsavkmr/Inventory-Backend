package kyro.inventory.model;

/**
 * Created by fahrur on 11/24/2016.
 */
public enum TransactionType {

    ORDER("ORDER"),
    RECEIVE("RECEIVE"),
    RETURN("RETURN");

    private final String text;

    /**
     * @param text
     */
    private TransactionType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
