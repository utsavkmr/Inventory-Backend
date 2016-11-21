package kyro.inventory.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Auditable entity
 *
 * @author fahrur
 * @version 1.0
 */
@MappedSuperclass
public abstract class AuditableEntity extends LookupEntity {
    /**
     * Created by
     */
    @Basic
    private String createdBy;

    /**
     * Created date
     */
    @Basic
    private Date createdDate;

    /**
     * Updated by
     */
    @Basic
    private String updatedBy;

    /**
     * Updated date
     */
    @Basic
    private Date updatedDate;

    /**
     * Get the created by
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set the created by
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get the created date
     * @return the created date
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set the created date
     * @param createdDate the created date
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get the updated by
     * @return the updated by
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Set the updated by
     * @param updatedBy the updated by
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Get updated date
     * @return the updated date
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Set the updated date
     * @param updatedDate the updated date
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Empty constructor
     */
    public AuditableEntity() {}
}
