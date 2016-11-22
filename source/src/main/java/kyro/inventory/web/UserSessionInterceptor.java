package kyro.inventory.web;

import kyro.inventory.model.LoginUserThread;
import kyro.inventory.model.UserLogin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fahrur on 11/22/2016.
 */
public class UserSessionInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        UserLogin userLogin = new UserLogin();
        userLogin.setUsername("test");
        userLogin.setId(1L);

        LoginUserThread loginUserThread = new LoginUserThread();
        loginUserThread.loginUser.set(userLogin);

        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
