package pl.com.itsystems.cookbook.recipe;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    public List<Recipe> findFirst10ByOrderByLikesDesc();

    public List<Recipe> findAllByOrderByTitleAsc();

    public List<Recipe> findAllByCategoryId(Long id);
}
