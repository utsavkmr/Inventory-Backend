package kyro.inventory.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fahrur on 11/29/2016.
 */
@Entity(name="AccCheckpoint")
@Table(name="acc_checkpoint")
public class AccCheckpoint extends IdentifiableEntity {

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

    @Basic
    private Boolean closed;

    @Basic
    private Long closingId;

    public AccCheckpoint() {}

    public AccCheckpoint(
            Long id, Long accountId, Double amount, Double balanceAfter,
            Long lastTransactionEntityId, Long lastTransactionChildId, TransactionType lastTransactionType,
            Date lastTransactionDateTime, Boolean closed, Long closingId
    ) {
        this.setId(id);
        this.accountId = accountId;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.lastTransactionChildId = lastTransactionChildId;
        this.lastTransactionEntityId = lastTransactionEntityId;
        this.lastTransactionType = lastTransactionType;
        this.lastTransactionDateTime = lastTransactionDateTime;
        this.closed = closed;
        this.closingId = closingId;
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

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Long getClosingId() {
        return closingId;
    }

    public void setClosingId(Long closingId) {
        this.closingId = closingId;
    }
}
