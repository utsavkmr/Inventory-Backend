package kyro.inventory.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ProductCategory
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="ProductCategory")
@Table(name="product_category")
public class ProductCategory extends ActiveAuditableEntity {

    /**
     * Empty constructor
     */
    public ProductCategory() {}
}
