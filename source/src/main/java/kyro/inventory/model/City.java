package kyro.inventory.model;

import javax.persistence.*;

/**
 * City
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="City")
@Table(name="city")
public class City extends ActiveAuditableEntity {

    /**
     * Empty constructor
     */
    public City() {}
}
