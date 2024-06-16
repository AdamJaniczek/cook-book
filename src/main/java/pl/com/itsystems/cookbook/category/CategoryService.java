package pl.com.itsystems.cookbook.category;

import org.springframework.stereotype.Service;
import pl.com.itsystems.cookbook.recipe.Recipe;
import pl.com.itsystems.cookbook.recipe.RecipeRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private static final Long NO_CATEGORY_ID = 0L;

    public CategoryService(CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        List<Recipe> recipes = recipeRepository.findAllByCategoryId(id);
        Category noCategory = categoryRepository.findById(NO_CATEGORY_ID).orElse(null);
        for (Recipe recipe : recipes) {
            recipe.setCategory(noCategory);
        }

        recipeRepository.saveAll(recipes);
        if (id > NO_CATEGORY_ID) {
            categoryRepository.deleteById(id);
        }
    }
}
