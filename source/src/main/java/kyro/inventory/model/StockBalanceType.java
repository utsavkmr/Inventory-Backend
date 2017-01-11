package kyro.inventory.model;

/**
 * Stock Balance Type
 *
 * @author fahrur
 * @version 1.0
 */
public enum StockBalanceType {

    ON_ORDER("ON_ORDER"),
    ON_HAND("ON_HAND"),
    RETURN("RETURN"),
    SALES("SALES");

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
