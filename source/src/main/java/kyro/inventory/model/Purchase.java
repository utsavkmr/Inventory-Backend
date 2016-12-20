package kyro.inventory.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Purchase
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="Purchase")
@Table(name="purchase")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@Purchase")
public class Purchase extends IdentifiableEntity {

    /**
     * No
     */
    @Basic
    @NotNull
    private String no;

    /**
     * No vendor
     */
    @Basic
    private String noVendor;

    /**
     * Date
     */
    @Basic
    @NotNull
    private Date date;

    /**
     * Due Date
     */
    @Basic
    private Date dueDate;

    /**
     * Vendor
     */
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="vendorId")
    private Vendor vendor;

    /**
     * Location
     */
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="locationId")
    private Location location;

    /**
     * Is Fulfilled
     */
    @Basic
    @NotNull
    private Boolean isFulfilled;

    /**
     * Is Paid
     */
    @Basic
    @NotNull
    private Boolean isPaid;

    /**
     * Orders
     */
    @OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="purchaseId", nullable = false)
    private List<OrderDetails> orders;

    /**
     * Receive details list
     */
    @OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="purchaseId", nullable = false)
    private List<ReceiveDetails> receiveDetailsList;

    /**
     * Sub Total
     */
    @Basic
    @NotNull
    private Double subTotal;

    /**
     * Tax Percent
     */
    @Basic
    @NotNull
    private Double taxPercent;

    /**
     * Tax Total
     */
    @Basic
    @NotNull
    private Double taxTotal;

    /**
     * Total
     */
    @Basic
    @NotNull
    private Double total;

    /**
     * Freight
     */
    @Basic
    @NotNull
    private Double freight;

    /**
     * Closed
     */
    @Basic
    @NotNull
    private Boolean closed;

    /**
     * Receiving
     */
    @Transient
    private Boolean receiving;

    /**
     * Purchase
     */
    public Purchase() {}

    /**
     * Get the no
     * @return the no
     */
    public String getNo() {
        return no;
    }

    /**
     * Set the no
     * @param no the no
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     * Get the no vendor
     * @return the no vendor
     */
    public String getNoVendor() {
        return noVendor;
    }

    /**
     * Set the no vendor
     * @param noVendor the no vendor
     */
    public void setNoVendor(String noVendor) {
        this.noVendor = noVendor;
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
     * Get the due date
     * @return the due date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Set the due date
     * @param dueDate the due date
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Get the vendor
     * @return the vendor
     */
    public Vendor getVendor() {
        return vendor;
    }

    /**
     * Set the vendor
     * @param vendor the vendor
     */
    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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
     * Get fulfilled
     * @return  fulfilled
     */
    public Boolean getFulfilled() {
        return isFulfilled;
    }

    /**
     * Set fulfilled
     * @param fulfilled fulfilled
     */
    public void setFulfilled(Boolean fulfilled) {
        isFulfilled = fulfilled;
    }

    /**
     * Get paid
     * @return paid
     */
    public Boolean getPaid() {
        return isPaid;
    }

    /**
     * Set paid
     * @param paid paid
     */
    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    /**
     * Get the orders
     * @return the orders
     */
    public List<OrderDetails> getOrders() {
        return orders;
    }

    /**
     * Set the orders
     * @param orders the orders
     */
    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
    }

    /**
     * Get the receive details list
     * @return the receive details list
     */
    public List<ReceiveDetails> getReceiveDetailsList() {
        return receiveDetailsList;
    }

    /**
     * Set the receive details list
     * @param receiveDetailsList the receive details list
     */
    public void setReceiveDetailsList(List<ReceiveDetails> receiveDetailsList) {
        this.receiveDetailsList = receiveDetailsList;
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
     * @param subTotal  the sub total
     */
    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Get the tax percent
     * @return the tax percent
     */
    public Double getTaxPercent() {
        return taxPercent;
    }

    /**
     * Set the tax percent
     * @param taxPercent the tax percent
     */
    public void setTaxPercent(Double taxPercent) {
        this.taxPercent = taxPercent;
    }

    /**
     * Get the tax total
     * @return the tax total
     */
    public Double getTaxTotal() {
        return taxTotal;
    }

    /**
     * Set the tax total
     * @param taxTotal the tax total
     */
    public void setTaxTotal(Double taxTotal) {
        this.taxTotal = taxTotal;
    }

    /**
     * Get total
     * @return total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Set total
     * @param total total
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * Get the freight
     * @return the freight
     */
    public Double getFreight() {
        return freight;
    }

    /**
     * Set the freight
     * @param freight the freight
     */
    public void setFreight(Double freight) {
        this.freight = freight;
    }

    /**
     * Get closed
     * @return closed
     */
    public Boolean getClosed() {
        return closed;
    }

    /**
     * Set closed
     * @param closed closed
     */
    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    /**
     * Get the receiving
     * @return the receiving
     */
    public Boolean getReceiving() {
        return receiving;
    }

    /**
     * Set the receiving
     * @param receiving the receiving
     */
    public void setReceiving(Boolean receiving) {
        this.receiving = receiving;
    }
}
