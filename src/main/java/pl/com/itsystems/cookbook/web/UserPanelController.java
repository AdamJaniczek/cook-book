package pl.com.itsystems.cookbook.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.com.itsystems.cookbook.user.User;
import pl.com.itsystems.cookbook.user.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user-panel")
public class UserPanelController {
    private final UserService userService;

    public UserPanelController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userPanel(Model model, Principal principal) {
        String eMail = principal.getName();
        userService.findByEmail(eMail).ifPresent(
                user -> model.addAttribute("user", user)
        );
        return "/user-panel/index";
    }

    @PostMapping
    public String updateUserPanel(@ModelAttribute("user") User user, String newPassword) {
        System.out.println("zapis");
        userService.save(user, newPassword);
        return "redirect:/user-panel";
    }
}
