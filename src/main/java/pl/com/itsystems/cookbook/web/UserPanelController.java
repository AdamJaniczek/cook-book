package pl.com.itsystems.cookbook.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        // Optional<UserCredentialsDto> userCredentialsDto = userService.findCredentialsByEmail(authentication.getName());
        String eMail = principal.getName();
        //Optional<User> user = userService.findByEmail(eMail);
        userService.findByEmail(eMail).ifPresent(
                user -> model.addAttribute("user", user)
        );
        return "/user-panel/index";
    }

    @PostMapping
    public String updateUserPanel(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password) {
        userService.findByEmail(email).ifPresent(
                user -> {
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);
                    user.setPassword(password);
                    userService.save(user);
                }
        );
        return "redirect:/";
    }
}
