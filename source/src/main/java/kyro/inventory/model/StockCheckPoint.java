package kyro.inventory.model;

import java.util.Date;

/**
 * Created by fahrur on 11/24/2016.
 */
public class StockCheckPoint {

    public Long id;

    public Long accountId;

    public Double amount;

    public Double balanceAfter;

    public Long lastTransactionEntityId;

    public Long lastTransactionChildId;

    public TransactionType lastTransactionType;

    public Date lastTransactionDateTime;

    public StockCheckPoint(
            Long id, Long accountId, Double amount, Double balanceAfter,
            Long lastTransactionEntityId, Long lastTransactionChildId, TransactionType lastTransactionType,
            Date lastTransactionDateTime
    ) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.lastTransactionChildId = lastTransactionChildId;
        this.lastTransactionEntityId = lastTransactionEntityId;
        this.lastTransactionType = lastTransactionType;
        this.lastTransactionDateTime = lastTransactionDateTime;
    }


}
