package kyro.inventory.model;

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
@Entity(name = "purchase")
public class Purchase extends IdentifiableEntity {

    @Basic
    @NotNull
    private String no;

    @Basic
    private String noVendor;

    @Basic
    @NotNull
    private Date date;

    @Basic
    private Date dueDate;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="vendorId")
    private Vendor vendor;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="locationId")
    private Location location;

    @Basic
    @NotNull
    private Boolean isFulfilled;

    @Basic
    @NotNull
    private Boolean isPaid;

    @OneToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="purchaseId", nullable = false)
    private List<OrderDetails> orders;

    @Basic
    @NotNull
    private Double subTotal;

    @Basic
    @NotNull
    private Double taxPercent;

    @Basic
    @NotNull
    private Double taxTotal;

    @Basic
    @NotNull
    private Double freight;

    public Purchase() {}

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNoVendor() {
        return noVendor;
    }

    public void setNoVendor(String noVendor) {
        this.noVendor = noVendor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Boolean getFulfilled() {
        return isFulfilled;
    }

    public void setFulfilled(Boolean fulfilled) {
        isFulfilled = fulfilled;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public List<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(Double taxPercent) {
        this.taxPercent = taxPercent;
    }

    public Double getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(Double taxTotal) {
        this.taxTotal = taxTotal;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }
}