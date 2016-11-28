package kyro.inventory.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fahrur on 11/24/2016.
 */
@Entity(name = "StockCheckpoint")
@Table(name="stock_checkpoint")
public class StockCheckPoint extends IdentifiableEntity {

    @Basic
    private Long accountId;

    @Basic
    private Double amount;

    @Basic
    private Double balanceAfter;

    @Basic
    private Long lastTransactionEntityId;

    @Basic
    private Long lastTransactionChildId;

    @Enumerated(EnumType.STRING)
    private TransactionType lastTransactionType;

    @Basic
    private Date lastTransactionDateTime;

    public StockCheckPoint() {}

    public StockCheckPoint(
            Long id, Long accountId, Double amount, Double balanceAfter,
            Long lastTransactionEntityId, Long lastTransactionChildId, TransactionType lastTransactionType,
            Date lastTransactionDateTime
    ) {
        this.setId(id);
        this.accountId = accountId;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.lastTransactionChildId = lastTransactionChildId;
        this.lastTransactionEntityId = lastTransactionEntityId;
        this.lastTransactionType = lastTransactionType;
        this.lastTransactionDateTime = lastTransactionDateTime;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public Long getLastTransactionEntityId() {
        return lastTransactionEntityId;
    }

    public void setLastTransactionEntityId(Long lastTransactionEntityId) {
        this.lastTransactionEntityId = lastTransactionEntityId;
    }

    public Long getLastTransactionChildId() {
        return lastTransactionChildId;
    }

    public void setLastTransactionChildId(Long lastTransactionChildId) {
        this.lastTransactionChildId = lastTransactionChildId;
    }

    public TransactionType getLastTransactionType() {
        return lastTransactionType;
    }

    public void setLastTransactionType(TransactionType lastTransactionType) {
        this.lastTransactionType = lastTransactionType;
    }

    public Date getLastTransactionDateTime() {
        return lastTransactionDateTime;
    }

    public void setLastTransactionDateTime(Date lastTransactionDateTime) {
        this.lastTransactionDateTime = lastTransactionDateTime;
    }
}
