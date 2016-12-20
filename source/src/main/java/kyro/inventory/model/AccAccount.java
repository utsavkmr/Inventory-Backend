package kyro.inventory.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Acc Account
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="AccAccount")
@Table(name="acc_account")
public class AccAccount extends IdentifiableEntity {

    /**
     * Product id
     */
    @Basic
    private Long productId;

    /**
     * Account Code
     */
    @Basic
    private String accountCode;

    /**
     * Empty Constructor
     */
    public AccAccount() {}

    /**
     * Acc Account Constructor
     * @param id the id
     * @param productId the product id
     * @param accountCode the product code
     */
    public AccAccount(Long id,Long productId, String accountCode) {
        this.setId(id);
        this.productId = productId;
        this.accountCode = accountCode;
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
     * Get the account code
     * @return the account code
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * Set the account code
     * @param accountCode the account code
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }
}
