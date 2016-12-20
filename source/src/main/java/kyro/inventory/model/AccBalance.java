package kyro.inventory.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Acc Balance
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="AccBalance")
@Table(name="acc_balance")
public class AccBalance extends IdentifiableEntity {

    /**
     * Account id
     */
    @Basic
    private Long accountId;

    /**
     * Balance
     */
    @Basic
    private Double balance;

    /**
     * Last Transaction Entity Id
     */
    @Basic
    private Long lastTransactionEntityId;

    /**
     * Last Transaction Child id
     */
    @Basic
    private Long lastTransactionChildId;

    /**
     * Last Transaction type
     */
    @Enumerated(EnumType.STRING)
    private TransactionType lastTransactionType;

    /**
     * Last transaction date time
     */
    @Basic
    private Date lastTransactionDateTime;

    /**
     * Empty constructor
     */
    public AccBalance() {}

    /**
     * Acc Balance constructor
     * @param id the id
     * @param accountId the account id
     * @param balance the balance
     * @param lastTransactionEntityId the last transaction entity id
     * @param lastTransactionChildId the last transaction child id
     * @param lastTransactionType the last transaction type
     * @param lastTransactionDateTime the last transaction date time
     */
    public AccBalance(
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
     * Get the balance
     * @return the balance
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * Set the balance
     * @param balance the balance
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * Get the last transaction entity id
     * @return The last transaction entity id
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
     * @param lastTransactionDateTime
     */
    public void setLastTransactionDateTime(Date lastTransactionDateTime) {
        this.lastTransactionDateTime = lastTransactionDateTime;
    }

}
