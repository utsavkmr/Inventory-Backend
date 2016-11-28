package kyro.inventory.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fahrur on 11/24/2016.
 */
@Entity(name="StockBalance")
@Table(name="stock_balance")
public class StockBalance extends IdentifiableEntity {

    @Basic
    private Long accountId;

    @Basic
    private Double balance;

    @Basic
    private Long lastTransactionEntityId;

    @Basic
    private Long lastTransactionChildId;

    @Enumerated(EnumType.STRING)
    private TransactionType lastTransactionType;

    @Basic
    private Date lastTransactionDateTime;

    public StockBalance() {}

    public StockBalance(
            Long id, Long accountId, Double balance,
            Long lastTransactionEntityId, Long lastTransactionChildId, TransactionType lastTransactionType,
            Date lastTransactionDateTime
    ) {
        this.setId(id);
        this.accountId = accountId;
        this.balance = balance;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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
