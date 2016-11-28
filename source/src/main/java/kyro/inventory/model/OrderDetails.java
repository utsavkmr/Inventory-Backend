package kyro.inventory.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Purchase
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="OrderDetails")
@Table(name="order_details")
public class OrderDetails extends IdentifiableEntity {

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

    @Basic
    @NotNull
    private Double unitPrice;

    @Basic
    @NotNull
    private Double discount;

    @Basic
    @NotNull
    private Double subTotal;

    @Basic
    @NotNull
    private Date date;

    public OrderDetails() {}

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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
