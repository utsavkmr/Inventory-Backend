package kyro.inventory.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Measurement
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name = "measurement")
public class Measurement extends ActiveAuditableEntity {

    /**
     * Abbreviation
     */
    @Basic
    @NotNull
    private String abbreviation;

    /**
     * Empty Constructor
     */
    public Measurement() {}

    /**
     * Get the abbreviation
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Set the abbreviation
     * @param abbreviation the abbreviation
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
