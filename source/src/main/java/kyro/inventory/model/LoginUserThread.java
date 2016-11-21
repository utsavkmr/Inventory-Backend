package kyro.inventory.model;

/**
 * Created by fahrur on 11/21/2016.
 */
public class LoginUserThread {

    public static final ThreadLocal<UserLogin> loginUser = new ThreadLocal<UserLogin>();

}
