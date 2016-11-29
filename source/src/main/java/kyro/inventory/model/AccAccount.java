package kyro.inventory.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fahrur on 11/29/2016.
 */
@Entity(name="AccAccount")
@Table(name="acc_account")
public class AccAccount extends IdentifiableEntity {

    @Basic
    private Long productId;

    @Basic
    private String accountCode;

    public AccAccount() {}

    public AccAccount(Long id,Long productId, String accountCode) {
        this.setId(id);
        this.productId = productId;
        this.accountCode = accountCode;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }
}
