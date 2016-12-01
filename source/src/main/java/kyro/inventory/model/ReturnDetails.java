package kyro.inventory.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by fahrur on 12/1/2016.
 */
@Entity(name="ReturnDetails")
@Table(name="return_details")
public class ReturnDetails extends IdentifiableEntity {

    @Column(name="purchaseId",insertable=false, updatable=false)
    private Long purchaseId;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="productId")
    @NotNull
    private Product product;

    @Basic
    @NotNull
    private Double quantityUOM;

    @Basic
    @NotNull
    private Double quantity;

    @Basic
    @NotNull
    private Boolean usePurchaseUOM;

    @Basic
    @NotNull
    private Double purchaseUOMConversion;

    public ReturnDetails() {}

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getQuantityUOM() {
        return quantityUOM;
    }

    public void setQuantityUOM(Double quantityUOM) {
        this.quantityUOM = quantityUOM;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Boolean getUsePurchaseUOM() {
        return usePurchaseUOM;
    }

    public void setUsePurchaseUOM(Boolean usePurchaseUOM) {
        this.usePurchaseUOM = usePurchaseUOM;
    }

    public Double getPurchaseUOMConversion() {
        return purchaseUOMConversion;
    }

    public void setPurchaseUOMConversion(Double purchaseUOMConversion) {
        this.purchaseUOMConversion = purchaseUOMConversion;
    }
}
