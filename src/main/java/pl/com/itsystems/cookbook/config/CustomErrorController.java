package pl.com.itsystems.cookbook.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    private CustomErrorService customErrorService;

    public CustomErrorController(CustomErrorService customErrorService) {
        this.customErrorService = customErrorService;
    }

    @RequestMapping("/error")
    public String error(Model model, HttpServletRequest request) {
        String message = customErrorService.generateErrorCode(request);
        model.addAttribute("message", message);
        return "message-status";
    }
}
