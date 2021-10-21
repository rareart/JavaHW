package dao;

import model.Ingredient;

public interface IngredientDAO {

    Ingredient findIngredientById(int id, int recipe_id);

    boolean addIngredient(Ingredient ingredient);

    boolean removeIngredientById(int id);
}
