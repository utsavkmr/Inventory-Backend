package kyro.inventory.model;

/**
 * User Login
 *
 * @author fahrur
 * @version 1.0
 */
public class UserLogin {

    /**
     * Id
     */
    private Long id;

    /**
     * Username
     */
    private String username;

    /**
     * Empty constructor
     */
    public UserLogin() {}

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
     * Get the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
