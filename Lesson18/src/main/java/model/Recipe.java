package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Recipe {
    private int id;
    private String name;
    private char[] description;
    private char[] instructions;
    private byte[] photo;
    private Map<Ingredient, IngredientAmount> ingredients;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getDescription() {
        return description;
    }

    public void setDescription(char[] description) {
        this.description = description;
    }

    public char[] getInstructions() {
        return instructions;
    }

    public void setInstructions(char[] instructions) {
        this.instructions = instructions;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Map<Ingredient, IngredientAmount> getIngredients() {
        return new HashMap<>(this.ingredients);
    }

    public void setIngredients(Map<Ingredient, IngredientAmount> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description=" + Arrays.toString(description) +
                ", instructions=" + Arrays.toString(instructions) +
                ", " + (photo != null ? "photo available" : "photo not available") +
                ", ingredients=" + ingredients +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
