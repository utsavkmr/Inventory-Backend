package kyro.inventory.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity(name="Sales")
@Table(name="sales")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@Sales")
public class Sales extends IdentifiableEntity {

    @Basic
    @NotNull
    private String no;

    @Basic
    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date date;

    @Basic
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date dueDate;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="customerId")
    private Customer customer;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="locationId")
    private Location location;

    @Basic
    @NotNull
    private Boolean isPaid;

    @OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="salesId", nullable = false)
    private List<SalesDetails> salesList;

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
     * Purchase
     */
    public Sales() {}

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public List<SalesDetails> getSalesList() {
        return salesList;
    }

    public void setSalesList(List<SalesDetails> salesList) {
        this.salesList = salesList;
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
