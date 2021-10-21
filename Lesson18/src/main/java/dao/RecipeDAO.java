package dao;

import model.Recipe;

import java.util.Set;

public interface RecipeDAO {

    Recipe findRecipe(String name);

    Set<Recipe> findRecipesByKeyword(String keyword);

    Recipe findRecipe(int id);

    int[] addRecipes(Recipe... recipes);

    void addPhotoToRecipe(String name, byte[] photo);

    void addPhotoToRecipe(int id, byte[] photo);

    boolean removeRecipe(String name);

    boolean removeRecipe(int id);
}
