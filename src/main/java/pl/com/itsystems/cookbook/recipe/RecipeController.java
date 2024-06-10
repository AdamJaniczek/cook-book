package pl.com.itsystems.cookbook.recipe;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.com.itsystems.cookbook.category.CategoryService;

@Controller
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAllRecipes(Model model) {
        Iterable<Recipe> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipes);
        return "recipes";
    }

    @GetMapping("/{id}")
    public String getRecipeById(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "recipe";
    }

    @GetMapping("/new")
    public String createRecipeForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "create_recipe";
    }

    @PostMapping
    public String saveRecipe(@ModelAttribute Recipe recipe) {
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes";
    }

    @GetMapping("/edit/{id}")
    public String editRecipeForm(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "edit_recipe";
    }

    @PostMapping("/{id}")
    public String updateRecipe(@PathVariable Long id, @ModelAttribute Recipe recipe) {
        recipe.setId(id);
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes";
    }

    @PostMapping("/{id}/like")
    public String updateRecipeLikeIt(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        recipe.likeIt();
        recipeService.saveRecipe(recipe);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return "redirect:/recipes";
    }
}
