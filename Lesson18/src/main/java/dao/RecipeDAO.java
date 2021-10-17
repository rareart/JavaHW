package dao;

import model.Recipe;

import java.util.Set;

public interface RecipeDAO {

    Recipe findRecipeByName(String name);

    Set<Recipe> findRecipesByKeyword(String keyword);

    Recipe findRecipeById(int id);

    void addRecipes(Recipe... recipes);

    void addPhotoToRecipe(byte[] photo);

    boolean removeRecipeByName(String name);

    boolean removeRecipeById(int id);
}
