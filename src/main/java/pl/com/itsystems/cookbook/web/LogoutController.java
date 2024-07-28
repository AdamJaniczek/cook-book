package pl.com.itsystems.cookbook.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout-success")
    public String logoutSuccess(Model model) {
        model.addAttribute("message", "Wylogowanie użytkownika przebiegło pomyślnie!");
        return "message-status";
    }
}
