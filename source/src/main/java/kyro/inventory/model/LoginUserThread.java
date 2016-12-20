package kyro.inventory.model;

/**
 * Login User Thread
 *
 * @author fahrur
 * @version 1.0
 */
public class LoginUserThread {

    /**
     * Login user
     */
    public static final ThreadLocal<UserLogin> loginUser = new ThreadLocal<UserLogin>();

}
