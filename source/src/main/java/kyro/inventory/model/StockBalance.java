package kyro.inventory.model;

import java.util.Date;

/**
 * Created by fahrur on 11/24/2016.
 */
public class StockBalance {

    public Long id;

    public Long accountId;

    public Double balance;

    public Long lastTransactionEntityId;

    public Long lastTransactionChildId;

    public TransactionType lastTransactionType;

    public Date lastTransactionDateTime;

    public StockBalance(
            Long id, Long accountId, Double balance,
            Long lastTransactionEntityId, Long lastTransactionChildId, TransactionType lastTransactionType,
            Date lastTransactionDateTime
    ) {
        this.id = id;
        this.accountId = accountId;
        this.balance = balance;
        this.lastTransactionChildId = lastTransactionChildId;
        this.lastTransactionEntityId = lastTransactionEntityId;
        this.lastTransactionType = lastTransactionType;
        this.lastTransactionDateTime = lastTransactionDateTime;
    }


}
