package kyro.inventory.model;

/**
 * Created by fahrur on 11/21/2016.
 */
public class UserLogin {

    private Long id;

    private String username;

    public UserLogin() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
