package kyro.inventory.model;

import javax.persistence.*;

/**
 * Country
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="Country")
@Table(name="country")
public class Country extends ActiveAuditableEntity {

    /**
     * Empty constructor
     */
    public Country() {}
}
