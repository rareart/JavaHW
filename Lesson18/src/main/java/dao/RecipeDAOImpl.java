package dao;

import model.Ingredient;
import model.Recipe;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeDAOImpl implements RecipeDAO {

    private final String SQL_SELECT_ID_BY_NAME = "SELECT id FROM RECIPE WHERE name = ?";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM RECIPE WHERE id = ?";
    private final String SQL_SELECT_INGREDIENT_ID_BY_NAME = "SELECT id FROM INGREDIENT WHERE name = ?";
    private final String SQL_SELECT_INGREDIENT_ID_BY_RECIPE_ID = "SELECT ingredient_id FROM RECIPE_INGREDIENT WHERE recipe_id = ?";
    private final String SQL_SELECT_ID_BY_KEYWORD = "SELECT id FROM RECIPE WHERE name LIKE %?%";
    private final String SQL_UPDATE_PHOTO_BY_ID = "UPDATE RECIPE SET photo = ? WHERE id = ?";
    private final String SQL_INSERT_INGREDIENT = "INSERT INTO INGREDIENT (name) VALUES (?)";
    private final String SQL_INSERT_RECIPE_INGREDIENT = "INSERT INTO RECIPE_INGREDIENT (recipe_id, ingredient_id, unit, amount) VALUES (?, ?, ?, ?)";
    private final String SQL_DELETE_RECIPE = "DELETE FROM RECIPE WHERE id = ?";
    private final String SQL_DELETE_RECIPE_INGREDIENT = "DELETE FROM RECIPE_INGREDIENT WHERE recipe_id = ?";
    private final String SQL_DELETE_INGREDIENTS = "DELETE i FROM INGREDIENT i LEFT JOIN RECIPE_INGREDIENT ri ON i.id = ri.recipe_id WHERE ri.recipe_id IS NULL";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations jdbcInsertRecipeOperations;
    private final LobHandler lobHandler;

    private final IngredientDAO ingredientDAO;

    public RecipeDAOImpl(JdbcTemplate jdbcTemplate,
                         SimpleJdbcInsertOperations jdbcInsertRecipeOperations,
                         LobHandler lobHandler,
                         IngredientDAO ingredientDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.lobHandler = lobHandler;
        this.jdbcInsertRecipeOperations = jdbcInsertRecipeOperations;

        this.ingredientDAO = ingredientDAO;
    }

    @Override
    public Recipe findRecipe(String name) {
        Integer id = jdbcTemplate.queryForObject(SQL_SELECT_ID_BY_NAME, Integer.class, name);
        return (id != null ? findRecipe(id) : null);
    }

    @Override
    public Set<Recipe> findRecipesByKeyword(String keyword) {
        Set<Recipe> recipes = new HashSet<>();
        List<Integer> recipes_id = jdbcTemplate.queryForList(SQL_SELECT_ID_BY_KEYWORD, Integer.class, keyword);
        recipes_id.forEach(id -> recipes.add(findRecipe(id)));
        return recipes;
    }

    @Override
    public Recipe findRecipe(int id) {
        List<Ingredient> ingredients = new ArrayList<>();
        List<Integer> ingredients_id = jdbcTemplate.queryForList(SQL_SELECT_INGREDIENT_ID_BY_RECIPE_ID, Integer.class, id);
        ingredients_id.forEach(ingredient_id -> ingredients.add(ingredientDAO.findIngredientById(ingredient_id, id)));

        RowMapper<Recipe> rowMapper = (rs, rowNum) -> {
            Recipe recipe = new Recipe();
            recipe.setId(id);
            recipe.setName(rs.getString(2));
            recipe.setDescription(lobHandler.getClobAsString(rs, 3));
            recipe.setInstructions(lobHandler.getClobAsString(rs, 4));
            recipe.setPhoto(lobHandler.getBlobAsBytes(rs, 5));
            recipe.setIngredients(ingredients);
            return recipe;
        };

        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int[] addRecipes(Recipe... recipes) {
        SqlParameterSource[] recipesParams = SqlParameterSourceUtils.createBatch(Arrays.stream(recipes).toArray());
        int [] id_array = jdbcInsertRecipeOperations.executeBatch(recipesParams);

        List<List<Ingredient>> ingredients = Arrays.stream(recipes).map(Recipe::getIngredients).collect(Collectors.toList());
        for (List<Ingredient> ingredientsList: ingredients){
            for (Ingredient ingredient : ingredientsList){
                try{
                    jdbcTemplate.queryForObject(SQL_SELECT_INGREDIENT_ID_BY_NAME, Integer.class, ingredient.getName());
                } catch (EmptyResultDataAccessException e){
                    jdbcTemplate.update(SQL_INSERT_INGREDIENT, ingredient.getName());
                }
            }
        }

        for (Recipe recipe: recipes){
            Integer recipe_id = jdbcTemplate.queryForObject(SQL_SELECT_ID_BY_NAME, Integer.class, recipe.getName());
            if (recipe_id != null){
                for(Ingredient ingredient : recipe.getIngredients()){
                    Integer ingredient_id = jdbcTemplate
                            .queryForObject(SQL_SELECT_INGREDIENT_ID_BY_NAME, Integer.class, ingredient.getName());
                    if (ingredient_id != null){
                        jdbcTemplate.update(
                                SQL_INSERT_RECIPE_INGREDIENT,
                                recipe_id,
                                ingredient_id,
                                ingredient.getIngredientAmount().getUnit(),
                                ingredient.getIngredientAmount().getAmount());
                    }
                }
            }
        }

        return id_array;
    }

    @Override
    public void addPhotoToRecipe(String name, byte[] photo) {
        Integer id = jdbcTemplate.queryForObject(SQL_SELECT_ID_BY_NAME, Integer.class, name);
        if(id != null){
            addPhotoToRecipe(id, photo);
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void addPhotoToRecipe(int id, byte[] photo) {
        jdbcTemplate.execute(SQL_UPDATE_PHOTO_BY_ID, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
            @Override
            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                lobCreator.setBlobAsBytes(ps, 1, photo);
                ps.setInt(2, id);
            }
        });
    }

    @Override
    public boolean removeRecipe(String name) {
        Integer id = jdbcTemplate.queryForObject(SQL_SELECT_ID_BY_NAME, Integer.class, name);
        return (id != null && removeRecipe(id));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public boolean removeRecipe(int id) {
        int result = jdbcTemplate.update(SQL_DELETE_RECIPE, id);
        jdbcTemplate.update(SQL_DELETE_RECIPE_INGREDIENT, id);
        jdbcTemplate.update(SQL_DELETE_INGREDIENTS);
        return result > 0;
    }
}
