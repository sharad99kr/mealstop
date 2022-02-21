package  com.dalhousie.MealStop.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model)
    {
        System.err.println("Login api called");
        return "user/login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        return "user/login-error";
    }
}