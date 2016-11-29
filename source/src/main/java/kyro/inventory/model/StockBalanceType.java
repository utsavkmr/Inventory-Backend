package kyro.inventory.model;

/**
 * Created by fahrur on 11/24/2016.
 */
public enum StockBalanceType {

    ON_ORDER("ON_ORDER"),
    RECEIVE("RECEIVE");

    private final String text;

    /**
     * @param text
     */
    private StockBalanceType(final String text) {
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
