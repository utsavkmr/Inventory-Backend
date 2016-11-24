package kyro.inventory.model;

/**
 * Created by fahrur on 11/24/2016.
 */
public class StockAccount {

    public Long id;

    public Long productId;

    public Long locationId;

    public StockBalanceType stockBalanceType;

    public StockAccount(Long id, Long productId, Long locationId, StockBalanceType type) {
        this.id = id;
        this.productId = productId;
        this.locationId = locationId;
        this.stockBalanceType = type;
    }
}
