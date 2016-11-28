package kyro.inventory.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Address
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="Address")
@Table(name="address")
public class Address extends IdentifiableEntity {

    /**
     * Street
     */
    @Basic
    private String street;

    /**
     * City
     */
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cityId")
    private City city;

    /**
     * Province
     */
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="provinceId")
    private Province province;

    /**
     * Country
     */
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="countryId")
    private Country country;

    /**
     * Postal Code
     */
    @Basic
    private String postalCode;

    /**
     * Remarks
     */
    @Basic
    private String remarks;


    /**
     * Empty Constructor
     */
    public Address() {}

    /**
     * Get the street
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Set the street
     * @param street the street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Get the city
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * Set the city
     * @param city the city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Get the province
     * @return the province
     */
    public Province getProvince() {
        return province;
    }

    /**
     * Set the province
     * @param province the province
     */
    public void setProvince(Province province) {
        this.province = province;
    }

    /**
     * Get the country
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Set the country
     * @param country the country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Get the postal code
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Set the postal code
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
}
