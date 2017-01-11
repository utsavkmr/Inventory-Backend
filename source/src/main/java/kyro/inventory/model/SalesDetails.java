package kyro.inventory.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name="SalesDetails")
@Table(name="sales_details")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@SalesDetails")
public class SalesDetails extends IdentifiableEntity {

    /**
     * Sales Id
     */
    @Column(name="salesId",insertable=false, updatable=false)
    private Long salesId;

    /**
     * Location
     */
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="locationId")
    @NotNull
    private Location location;

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
     * Use Sales UOM
     */
    @Basic
    @NotNull
    private Boolean useSalesUOM;

    /**
     * Sales UOM Conversion
     */
    @Basic
    @NotNull
    private Double salesUOMConversion;

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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date date;

    /**
     * Empty Constructor
     */
    public SalesDetails() {}

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

    public Boolean getUseSalesUOM() {
        return useSalesUOM;
    }

    public void setUseSalesUOM(Boolean useSalesUOM) {
        this.useSalesUOM = useSalesUOM;
    }

    public Double getSalesUOMConversion() {
        return salesUOMConversion;
    }

    public void setSalesUOMConversion(Double salesUOMConversion) {
        this.salesUOMConversion = salesUOMConversion;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(Double discountTotal) {
        this.discountTotal = discountTotal;
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
