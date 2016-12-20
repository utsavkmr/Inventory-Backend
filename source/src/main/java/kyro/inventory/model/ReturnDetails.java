package kyro.inventory.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Return Details
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="ReturnDetails")
@Table(name="return_details")
public class ReturnDetails extends IdentifiableEntity {

    /**
     * Purchase id
     */
    @Column(name="purchaseId",insertable=false, updatable=false)
    private Long purchaseId;

    /**
     * Product id
     */
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="productId")
    @NotNull
    private Product product;

    /**
     * Quantity UOM
     */
    @Basic
    @NotNull
    private Double quantityUOM;

    /**
     * Quantity
     */
    @Basic
    @NotNull
    private Double quantity;

    /**
     * Use Purchase UOM
     */
    @Basic
    @NotNull
    private Boolean usePurchaseUOM;

    /**
     * Purchase UOM Conversion
     */
    @Basic
    @NotNull
    private Double purchaseUOMConversion;

    /**
     * Return Details
     */
    public ReturnDetails() {}

    /**
     * Get the purchase id
     * @return the purchase id
     */
    public Long getPurchaseId() {
        return purchaseId;
    }

    /**
     * Set the purchase id
     * @param purchaseId the purchase id
     */
    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    /**
     * Get the product
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set the product
     * @param product the product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Get the quantity UOM
     * @return the quantity UOM
     */
    public Double getQuantityUOM() {
        return quantityUOM;
    }

    /**
     * Set the quantity UOM
     * @param quantityUOM the quantity UOM
     */
    public void setQuantityUOM(Double quantityUOM) {
        this.quantityUOM = quantityUOM;
    }

    /**
     * Get the quantity
     * @return the quantity
     */
    public Double getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity
     * @param quantity the quantity
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    /**
     * Get use purchase uom
     * @return use purchase uom
     */
    public Boolean getUsePurchaseUOM() {
        return usePurchaseUOM;
    }

    /**
     * Set use purchase uom
     * @param usePurchaseUOM use purchase uom
     */
    public void setUsePurchaseUOM(Boolean usePurchaseUOM) {
        this.usePurchaseUOM = usePurchaseUOM;
    }

    /**
     * Get the purchase uom conversion
     * @return the purchase uom conversion
     */
    public Double getPurchaseUOMConversion() {
        return purchaseUOMConversion;
    }

    /**
     * Set the purchase uom conversion
     * @param purchaseUOMConversion the purchase uom conversion
     */
    public void setPurchaseUOMConversion(Double purchaseUOMConversion) {
        this.purchaseUOMConversion = purchaseUOMConversion;
    }
}
