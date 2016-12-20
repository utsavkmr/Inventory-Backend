package kyro.inventory.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Stock Account
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="StockAccount")
@Table(name="stock_account")
public class StockAccount extends IdentifiableEntity {

    /**
     * Product id
     */
    @Basic
    private Long productId;

    /**
     * Location id
     */
    @Basic
    private Long locationId;

    /**
     * Stock balance type
     */
    @Enumerated(EnumType.STRING)
    private StockBalanceType stockBalanceType;

    /**
     * Empty constructor
     */
    public StockAccount() {}

    /**
     * Constructor
     * @param id the product id
     * @param productId the product id
     * @param locationId the location id
     * @param type the type
     */
    public StockAccount(Long id, Long productId, Long locationId, StockBalanceType type) {
        this.setId(id);
        this.productId = productId;
        this.locationId = locationId;
        this.stockBalanceType = type;
    }

    /**
     * Get the product id
     * @return the product id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Set the product id
     * @param productId the product id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * Get the location id
     * @return the location id
     */
    public Long getLocationId() {
        return locationId;
    }

    /**
     * Set the location id
     * @param locationId the location id
     */
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    /**
     * Get the stock balance
     * @return the stock balance
     */
    public StockBalanceType getStockBalanceType() {
        return stockBalanceType;
    }

    /**
     * Set the stock balance
     * @param stockBalanceType the stock balance
     */
    public void setStockBalanceType(StockBalanceType stockBalanceType) {
        this.stockBalanceType = stockBalanceType;
    }
}
