package kyro.inventory.model;

import javax.persistence.Entity;

/**
 * Location
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name = "location")
public class Location extends ActiveAuditableEntity {

    /**
     * Empty constructor
     */
    public Location() {}
}
