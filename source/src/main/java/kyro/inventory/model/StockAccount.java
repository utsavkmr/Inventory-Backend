package kyro.inventory.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by fahrur on 11/24/2016.
 */
@Entity(name="StockAccount")
@Table(name="stock_account")
public class StockAccount extends IdentifiableEntity {

    @Basic
    private Long productId;

    @Basic
    private Long locationId;

    @Enumerated(EnumType.STRING)
    public StockBalanceType stockBalanceType;

    public StockAccount() {}

    public StockAccount(Long id, Long productId, Long locationId, StockBalanceType type) {
        this.setId(id);
        this.productId = productId;
        this.locationId = locationId;
        this.stockBalanceType = type;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public StockBalanceType getStockBalanceType() {
        return stockBalanceType;
    }

    public void setStockBalanceType(StockBalanceType stockBalanceType) {
        this.stockBalanceType = stockBalanceType;
    }
}
