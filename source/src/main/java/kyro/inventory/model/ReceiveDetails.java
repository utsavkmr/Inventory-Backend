package kyro.inventory.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by fahrur on 11/29/2016.
 */
@Entity(name="ReceiveDetails")
@Table(name="receive_details")
public class ReceiveDetails extends IdentifiableEntity {

    @Column(name="purchaseId",insertable=false, updatable=false)
    private Long purchaseId;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="productId")
    @NotNull
    private Product product;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="locationId")
    @NotNull
    private Location location;

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
    private Date receiveDate;

    public ReceiveDetails() {}

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
}
