package kyro.inventory.model;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Purchase
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="OrderDetails")
@Table(name="order_details")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@OrderDetails")
public class OrderDetails extends IdentifiableEntity {

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
     * User Purchase UOM
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
     * Unit Price
     */
    @Basic
    @NotNull
    private Double unitPrice;

    /**
     * Total Price
     */
    @Basic
    @NotNull
    private Double totalPrice;

    /**
     * Discount
     */
    @Basic
    @NotNull
    private Double discount;

    /**
     * Discount Total
     */
    @Basic
    @NotNull
    private Double discountTotal;

    /**
     * Sub Total
     */
    @Basic
    @NotNull
    private Double subTotal;

    /**
     * Date
     */
    @Basic
    @NotNull
    private Date date;

    /**
     * Return details
     */
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="returnDetailsId")
    private ReturnDetails returnDetails;

    /**
     * Receive details
     */
    @OneToOne(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name="receiveDetailsId")
    private ReceiveDetails receiveDetails;

    /**
     * Empty Constructor
     */
    public OrderDetails() {}

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

    public Double getPurchaseUOMConversion() {
        return purchaseUOMConversion;
    }

    /**
     * Set the purchse uom conversion
     * @param purchaseUOMConversion the purchse uom conversion
     */
    public void setPurchaseUOMConversion(Double purchaseUOMConversion) {
        this.purchaseUOMConversion = purchaseUOMConversion;
    }

    /**
     * Get the unit price
     * @return the unit price
     */
    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Set the unit price
     * @param unitPrice the unit price
     */
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Get the total price
     * @return the total price
     */
    public Double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Set the total price
     * @param totalPrice the total price
     */
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Get the discount
     * @return the discount
     */
    public Double getDiscount() {
        return discount;
    }

    /**
     * Set the discount
     * @param discount the discount
     */
    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    /**
     * Get the discount total
     * @return the discount total
     */
    public Double getDiscountTotal() {
        return discountTotal;
    }

    /**
     * Set the discount total
     * @param discountTotal the discount total
     */
    public void setDiscountTotal(Double discountTotal) {
        this.discountTotal = discountTotal;
    }

    /**
     * Get the sub total
     * @return the sub total
     */
    public Double getSubTotal() {
        return subTotal;
    }

    /**
     * Set the sub total
     * @param subTotal the sub total
     */
    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Get the date
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the date
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Get the return details
     * @return the return details
     */
    public ReturnDetails getReturnDetails() {
        return returnDetails;
    }

    /**
     * Set the return details
     * @param returnDetails the return details
     */
    public void setReturnDetails(ReturnDetails returnDetails) {
        this.returnDetails = returnDetails;
    }

    /**
     * Get the receive details
     * @return the receive details
     */
    public ReceiveDetails getReceiveDetails() {
        return receiveDetails;
    }

    /**
     * Set the receive details
     * @param receiveDetails the receive details
     */
    public void setReceiveDetails(ReceiveDetails receiveDetails) {
        this.receiveDetails = receiveDetails;
    }

}
