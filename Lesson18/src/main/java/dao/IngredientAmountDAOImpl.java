package dao;

import model.IngredientAmount;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;


public class IngredientAmountDAOImpl implements IngredientAmountDAO {

    private final String SQL_SELECT = "SELECT * FROM RECIPE_INGREDIENT WHERE recipe_id = ? and ingredient_id = ?";
    private final String SQL_INSERT = "INSERT INTO RECIPE_INGREDIENT (recipe_id, ingredient_id, unit, amount) values (:recipe_id, :ingredient_id, :unit, :amount)";
    private final String SQL_DELETE = "DELETE FROM RECIPE_INGREDIENT WHERE recipe_id = ? AND ingredient_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcOperations parameterJdbcOperations;
    private final RowMapper<IngredientAmount> ingredientAmountRowMapper;

    public IngredientAmountDAOImpl(JdbcTemplate jdbcTemplate,
                                   NamedParameterJdbcOperations parameterJdbcOperations,
                                   SimpleJdbcInsertOperations jdbcInsertIngredientAmountOperations) {
        this.jdbcTemplate = jdbcTemplate;
        this.parameterJdbcOperations = parameterJdbcOperations;
        this.ingredientAmountRowMapper = (rs, rowNum) -> {
            IngredientAmount ingredientAmount = new IngredientAmount();
            ingredientAmount.setId(rs.getInt(1));
            ingredientAmount.setUnit(rs.getString(4));
            ingredientAmount.setAmount(rs.getDouble(5));
            return ingredientAmount;
        };
    }

    @Override
    public IngredientAmount findIngredientAmount(int recipe_id, int ingredient_id) {
        return jdbcTemplate.queryForObject(
                SQL_SELECT,
                ingredientAmountRowMapper,
                recipe_id, ingredient_id
                );
    }

    @Override
    public boolean addIngredientAmount(IngredientAmount ingredientAmount, int recipe_id, int ingredient_id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("recipe_id", recipe_id)
                .addValue("ingredient_id", ingredient_id)
                .addValue("unit", ingredientAmount.getUnit())
                .addValue("amount", ingredientAmount.getAmount());
        int update = parameterJdbcOperations.update(SQL_INSERT, parameterSource);
        return update > 0;
    }

    @Override
    public boolean removeIngredientAmount(int recipe_id, int ingredient_id) {
        int update = jdbcTemplate.update(SQL_DELETE, recipe_id, ingredient_id);
        return update > 0;
    }
}
