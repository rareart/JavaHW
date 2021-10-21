package dao;

import model.Ingredient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Qualifier("IngredientDAO")
@Repository
public class IngredientDAOImpl implements IngredientDAO {

    private final String SQL_SELECT = "SELECT * FROM INGREDIENT WHERE id = ?";
    private final String SQL_UPDATE = "INSERT INTO INGREDIENT (name) VALUES (?)";
    private final String SQL_REMOVE = "DELETE FROM INGREDIENT WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    private final IngredientAmountDAO ingredientAmountDAO;

    public IngredientDAOImpl(JdbcTemplate jdbcTemplate,
                             IngredientAmountDAO ingredientAmountDAO) {
        this.jdbcTemplate = jdbcTemplate;

        this.ingredientAmountDAO = ingredientAmountDAO;
    }

    @Override
    public Ingredient findIngredientById(int id, int recipe_id) {
        RowMapper<Ingredient> rowMapper = (rs, rowNum) -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(rs.getInt(1));
            ingredient.setName(rs.getString(2));
            ingredient.setIngredientAmount(ingredientAmountDAO.findIngredientAmount(recipe_id, id));
            return ingredient;
        };
        return jdbcTemplate.queryForObject(SQL_SELECT, rowMapper, id);
    }

    @Override
    public boolean addIngredient(Ingredient ingredient) {
        int update = jdbcTemplate.update(SQL_UPDATE, ingredient.getName());
        return update > 0;
    }

    @Override
    public boolean removeIngredientById(int id) {
        int update = jdbcTemplate.update(SQL_REMOVE, id);
        return update > 0;
    }
}
