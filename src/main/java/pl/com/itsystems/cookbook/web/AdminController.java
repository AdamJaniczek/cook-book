package pl.com.itsystems.cookbook.web;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.com.itsystems.cookbook.user.UserRole;
import pl.com.itsystems.cookbook.user.UserService;
import pl.com.itsystems.cookbook.user.User;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin-panel")
class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    String adminPanel(Model model) {
        List<User> users = userService.findAllWithoutCurrentUser();
        model.addAttribute("users", users);
        return "/admin-panel/index";
    }

    @GetMapping("/edit")
    String editUser(String userEmail, Model model) {
        userService.findByEmail(userEmail).ifPresentOrElse(
                user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("userRoles", userService.findAllUserRoles());
                },
                () -> model.addAttribute("user", new User())
        );
        return "/admin-panel/edit-user";
    }

    @PostMapping("/edit")
    String updateUser(Set<UserRole> userRoles, User user) {
        userService.save(user, userRoles);
        return "redirect:/admin-panel";
    }

    @GetMapping("/delete-user")
    String deleteUser(@RequestParam String email) {
        userService.deleteUserByEmail(email);
        return "redirect:/admin";
    }
}