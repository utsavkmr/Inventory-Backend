package kyro.inventory.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * ReceiveDetails
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="ReceiveDetails")
@Table(name="receive_details")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@ReceiveDetails")
public class ReceiveDetails extends IdentifiableEntity {

    /**
     * Purchase Id
     */
    @Column(name="purchaseId",insertable=false, updatable=false)
    private Long purchaseId;

    /**
     * Product
     */
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="productId")
    @NotNull
    private Product product;

    /**
     * Location
     */
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="locationId")
    @NotNull
    private Location location;

    /**
     * Order Details
     */
    @OneToOne(mappedBy="receiveDetails")
    private OrderDetails orderDetails;

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
     * Receive date
     */
    @Basic
    @NotNull
    private Date receiveDate;

    /**
     * Empty constructor
     */
    public ReceiveDetails() {}

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
     * Get the location
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set the location
     * @param location the location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Get the order details
     * @return the order details
     */
    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    /**
     * Set the order details
     * @param orderDetails the order details
     */
    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    /**
     * Get the quantity uom
     * @return the quantity uom
     */
    public Double getQuantityUOM() {
        return quantityUOM;
    }

    /**
     * Set the quantity uom
     * @param quantityUOM the quantity uom
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
     * Get the use purchase uom
     * @return the use purchase uom
     */
    public Boolean getUsePurchaseUOM() {
        return usePurchaseUOM;
    }

    /**
     * Set the use purchase uom
     * @param usePurchaseUOM the use purchase uom
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
     * Set the purchase UOM Conversion
     * @param purchaseUOMConversion the purchase UOM Conversion
     */
    public void setPurchaseUOMConversion(Double purchaseUOMConversion) {
        this.purchaseUOMConversion = purchaseUOMConversion;
    }

    /**
     * Get the receive date
     * @return the receive date
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * Set the receive date
     * @param receiveDate the receive date
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
}
