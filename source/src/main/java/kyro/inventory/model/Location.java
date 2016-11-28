package kyro.inventory.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Location
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="Location")
@Table(name="location")
public class Location extends ActiveAuditableEntity {

    /**
     * Empty constructor
     */
    public Location() {}
}
