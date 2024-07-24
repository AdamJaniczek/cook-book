package pl.com.itsystems.cookbook.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.com.itsystems.cookbook.user.UserService;
import pl.com.itsystems.cookbook.user.dto.UserRegistrationDto;

@Controller
class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    String registrationForm(Model model) {
        UserRegistrationDto user = new UserRegistrationDto();
        model.addAttribute("user", user);
        return "registration-form";
    }

    @PostMapping("/register")
    String register(UserRegistrationDto userRegistrationDto) {
        userService.register(userRegistrationDto);
        return "redirect:/register-confirmation";
    }

    @GetMapping("/register-confirmation")
    String registrationConfirmation(Model model) {
        model.addAttribute("message", "Rejestracja użytkownika przebiegła przebiegła pomyślnie");
        return "message-status";
    }
}