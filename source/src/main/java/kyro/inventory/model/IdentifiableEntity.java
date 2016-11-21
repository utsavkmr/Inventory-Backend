package kyro.inventory.model;

import javax.persistence.*;

/**
 * Identifiable entity
 *
 * @author fahrur
 * @version 1.0
 */
@MappedSuperclass
public abstract class IdentifiableEntity {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    /**
     * Get the id
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Empty constructor
     */
    public IdentifiableEntity() {}
}
