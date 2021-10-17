package dao;

import model.Recipe;

import java.util.Set;

public class RecipeDAOImpl implements RecipeDAO {
    @Override
    public Recipe findRecipeByName(String name) {
        return null;
    }

    @Override
    public Set<Recipe> findRecipesByKeyword(String keyword) {
        return null;
    }

    @Override
    public Recipe findRecipeById(int id) {
        return null;
    }

    @Override
    public void addRecipes(Recipe... recipes) {

    }

    @Override
    public void addPhotoToRecipe(byte[] photo) {

    }

    @Override
    public boolean removeRecipeByName(String name) {
        return false;
    }

    @Override
    public boolean removeRecipeById(int id) {
        return false;
    }
}
