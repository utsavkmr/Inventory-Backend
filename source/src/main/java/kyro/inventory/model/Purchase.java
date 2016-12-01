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
@Entity(name="Purchase")
@Table(name="purchase")
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

    @OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="purchaseId", nullable = false)
    private List<OrderDetails> orders;

    @OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="purchaseId", nullable = false)
    private List<ReceiveDetails> receiveDetailsList;

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
    private Double total;

    @Basic
    @NotNull
    private Double freight;

    @Basic
    @NotNull
    private Boolean closed;

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

    public List<ReceiveDetails> getReceiveDetailsList() {
        return receiveDetailsList;
    }

    public void setReceiveDetailsList(List<ReceiveDetails> receiveDetailsList) {
        this.receiveDetailsList = receiveDetailsList;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
}
