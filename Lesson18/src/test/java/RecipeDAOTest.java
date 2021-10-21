import config.DAOComponentsConfiguration;
import dao.RecipeDAO;
import model.Ingredient;
import model.IngredientAmount;
import model.Recipe;
import org.h2.tools.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DAOComponentsConfiguration.class)
public class RecipeDAOTest {

    @BeforeAll
    public static void startServer() throws SQLException {
        Server.createTcpServer().start();
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RecipeDAO recipeDAO;

    @AfterEach
    public void clear(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "RECIPE_INGREDIENT", "INGREDIENT", "RECIPE");
    }

    @Test
    public void addAndFindRecipes(){
        IngredientAmount ingredientAmount1 = new IngredientAmount();
        ingredientAmount1.setUnit("kg");
        ingredientAmount1.setAmount(1.42D);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("flour");
        ingredient1.setIngredientAmount(ingredientAmount1);

        IngredientAmount ingredientAmount2 = new IngredientAmount();
        ingredientAmount2.setUnit("gram");
        ingredientAmount2.setAmount(20.5D);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("yeast");
        ingredient2.setIngredientAmount(ingredientAmount2);

        IngredientAmount ingredientAmount3 = new IngredientAmount();
        ingredientAmount3.setUnit("gram");
        ingredientAmount3.setAmount(5D);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setName("sugar");
        ingredient3.setIngredientAmount(ingredientAmount3);

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        ingredients.add(ingredient3);

        Recipe cake = new Recipe();
        cake.setIngredients(ingredients);
        cake.setName("Cake");
        cake.setDescription("Tasty cake!");
        cake.setInstructions("1. Put in the oven; 2. Remove; 3. It's done.");


        IngredientAmount ingredientAmount4 = new IngredientAmount();
        ingredientAmount4.setUnit("liter");
        ingredientAmount4.setAmount(0.3D);

        Ingredient ingredient4 = new Ingredient();
        ingredient4.setName("water");
        ingredient4.setIngredientAmount(ingredientAmount4);

        IngredientAmount ingredientAmount5 = new IngredientAmount();
        ingredientAmount5.setUnit("gram");
        ingredientAmount5.setAmount(5D);

        Ingredient ingredient5 = new Ingredient();
        ingredient5.setName("tea");
        ingredient5.setIngredientAmount(ingredientAmount5);

        List<Ingredient> ingredients2 = new ArrayList<>();
        ingredients2.add(ingredient3);
        ingredients2.add(ingredient4);
        ingredients2.add(ingredient5);

        Recipe tea = new Recipe();
        tea.setName("Tea");
        tea.setDescription("Earl gray tea");
        tea.setIngredients(ingredients2);

        recipeDAO.addRecipes(cake, tea);

        Recipe cakeFromDAO = recipeDAO.findRecipe("Cake");
        Recipe teaFromDAO = recipeDAO.findRecipe("Tea");

        System.out.println(cakeFromDAO.toString());
        System.out.println(teaFromDAO.toString());
    }

    //todo: another cases
}
