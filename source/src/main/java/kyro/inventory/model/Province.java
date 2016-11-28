package kyro.inventory.model;

import javax.persistence.*;

/**
 * Province
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="Province")
@Table(name="province")
public class Province extends ActiveAuditableEntity {

    /**
     * Empty constructor
     */
    public Province() {}
}
