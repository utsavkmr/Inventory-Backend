package kyro.inventory.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Stock Checkpoint
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name = "StockCheckpoint")
@Table(name="stock_checkpoint")
public class StockCheckpoint extends IdentifiableEntity {

    /**
     * Account id
     */
    @Basic
    private Long accountId;

    /**
     * Amount
     */
    @Basic
    private Double amount;

    /**
     * Balance After
     */
    @Basic
    private Double balanceAfter;

    /**
     * Last transaction entity id
     */
    @Basic
    private Long lastTransactionEntityId;

    /**
     * Last transaction child id
     */
    @Basic
    private Long lastTransactionChildId;

    /**
     * Last transaction type
     */
    @Enumerated(EnumType.STRING)
    private TransactionType lastTransactionType;

    /**
     * Last transaction type
     */
    @Basic
    private Date lastTransactionDateTime;

    /**
     * Closed
     */
    @Basic
    private Boolean closed;

    /**
     * Closing id
     */
    @Basic
    private Long closingId;

    /**
     * Empty constructor
     */
    public StockCheckpoint() {}

    /**
     * Constructor
     * @param id the id
     * @param accountId the account id
     * @param amount the amount
     * @param balanceAfter the balance after
     * @param lastTransactionEntityId the last transaction entity id
     * @param lastTransactionChildId the last transaction child id
     * @param lastTransactionType the last transaction type
     * @param lastTransactionDateTime the last transaction date time
     * @param closed closed
     * @param closingId closing id
     */
    public StockCheckpoint(
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

    /**
     * Get the account id
     * @return the account id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * Set the account id
     * @param accountId the account id
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * Get the amount
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Set the amount
     * @param amount the amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Get the balance after
     * @return the balance after
     */
    public Double getBalanceAfter() {
        return balanceAfter;
    }

    /**
     * Get the balance after
     * @param balanceAfter the balance after
     */
    public void setBalanceAfter(Double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    /**
     * Get the last transaction entity id
     * @return the last transaction entity id
     */
    public Long getLastTransactionEntityId() {
        return lastTransactionEntityId;
    }

    /**
     * Set the last transaction entity id
     * @param lastTransactionEntityId the last transaction entity id
     */
    public void setLastTransactionEntityId(Long lastTransactionEntityId) {
        this.lastTransactionEntityId = lastTransactionEntityId;
    }

    /**
     * Get the last transaction child id
     * @return the last transaction child id
     */
    public Long getLastTransactionChildId() {
        return lastTransactionChildId;
    }

    /**
     * Set the last transaction child id
     * @param lastTransactionChildId the last transaction child id
     */
    public void setLastTransactionChildId(Long lastTransactionChildId) {
        this.lastTransactionChildId = lastTransactionChildId;
    }

    /**
     * Get the last transaction type
     * @return the last transaction type
     */
    public TransactionType getLastTransactionType() {
        return lastTransactionType;
    }

    /**
     * Set the last transaction type
     * @param lastTransactionType the last transaction type
     */
    public void setLastTransactionType(TransactionType lastTransactionType) {
        this.lastTransactionType = lastTransactionType;
    }

    /**
     * Get the last transaction date time
     * @return the last transaction date time
     */
    public Date getLastTransactionDateTime() {
        return lastTransactionDateTime;
    }

    /**
     * Set the last transaction date time
     * @param lastTransactionDateTime the last transaction date time
     */
    public void setLastTransactionDateTime(Date lastTransactionDateTime) {
        this.lastTransactionDateTime = lastTransactionDateTime;
    }

    /**
     * Get closed
     * @return closed
     */
    public Boolean getClosed() {
        return closed;
    }

    /**
     * Set closed
     * @param closed closed
     */
    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    /**
     * Get the closing id
     * @return the closing id
     */
    public Long getClosingId() {
        return closingId;
    }

    /**
     * Set the closing id
     * @param closingId the closing id
     */
    public void setClosingId(Long closingId) {
        this.closingId = closingId;
    }
}
