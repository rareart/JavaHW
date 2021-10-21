package model;

import java.util.Objects;

public class Ingredient {
    private int id;
    private String name;
    private IngredientAmount ingredientAmount;

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

    public IngredientAmount getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(IngredientAmount ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredientAmount=" + ingredientAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
