package kyro.inventory.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Lookup entity
 *
 * @author fahrur
 * @version 1.0
 */
@MappedSuperclass
public abstract class LookupEntity extends IdentifiableEntity {

    /**
     * Name
     */
    @Basic
    @NotNull
    private String name;

    /**
     * Get the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Empty constructor
     */
    public LookupEntity() {}
}
