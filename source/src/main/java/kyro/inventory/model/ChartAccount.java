package kyro.inventory.model;

/**
 * Created by fahrur on 11/24/2016.
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
