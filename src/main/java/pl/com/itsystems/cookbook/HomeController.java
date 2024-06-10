package pl.com.itsystems.cookbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.com.itsystems.cookbook.recipe.RecipeRepository;
import pl.com.itsystems.cookbook.recipe.RecipeService;

@Controller
public class HomeController {
    private final RecipeService recipeService;

    public HomeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("topRecipes", recipeService.getTopRecipes());
        return "home";
    }
}
