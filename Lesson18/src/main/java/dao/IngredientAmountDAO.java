package dao;

import model.IngredientAmount;

public interface IngredientAmountDAO {

    IngredientAmount findIngredientAmount(int recipe_id, int ingredient_id);

    boolean addIngredientAmount(IngredientAmount ingredientAmount, int recipe_id, int ingredient_id);

    boolean removeIngredientAmount(int recipe_id, int ingredient_id);
}
