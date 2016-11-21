package kyro.inventory.model;

import javax.persistence.Entity;

/**
 * ProductCategory
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name = "product_category")
public class ProductCategory extends ActiveAuditableEntity {

    /**
     * Empty constructor
     */
    public ProductCategory() {}
}
