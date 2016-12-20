package kyro.inventory.model;

/**
 * ChartAccount
 *
 * @author fahrur
 * @version 1.0
 */
public enum ChartAccount {

    INV_STOCK("1310");

    private final String text;

    /**
     * @param text
     */
    private ChartAccount(final String text) {
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
