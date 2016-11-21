package kyro.inventory.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Active Auditable entity
 *
 * @author fahrur
 * @version 1.0
 */
@MappedSuperclass
public abstract class ActiveAuditableEntity extends AuditableEntity {

    /**
     * The is active
     */
    @Basic
    @NotNull
    private Boolean isActive;

    /**
     * Get the is active
     * @return the is active
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * Set the is active
     * @param isActive the is active
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Empty entity
     */
    public ActiveAuditableEntity() {}
}
