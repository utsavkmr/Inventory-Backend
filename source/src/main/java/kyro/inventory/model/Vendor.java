package kyro.inventory.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Vendor
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name = "vendor")
public class Vendor extends ActiveAuditableEntity {

    /**
     * Phone
     */
    @Basic
    private String phone;

    /**
     * Fax
     */
    @Basic
    private String fax;

    /**
     * Email
     */
    @Basic
    private String email;

    /**
     * Website
     */
    @Basic
    private String website;

    /**
     * Remarks
     */
    @Basic
    private String remarks;

    /**
     * Address
     */
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="addressId")
    @Cascade(CascadeType.ALL)
    private Address address;

    /**
     * Empty Constructor
     */
    public Vendor() {}

    /**
     * Get the phone
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the phone
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get the tax
     * @return the tax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Set the fax
     * @param fax the fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Get the email
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the website
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Set the website
     * @param website the website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Get the remarks
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Set the remarks
     * @param remarks the remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Get the address
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Set the address
     * @param address the address
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
