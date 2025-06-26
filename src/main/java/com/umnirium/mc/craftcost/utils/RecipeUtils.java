package com.umnirium.mc.craftcost.utils;

import org.bukkit.inventory.*;

import java.util.*;

public class RecipeUtils {
    public static Map<Recipe, Map<RecipeChoice, Integer>> ListIngredients(List<Recipe> recipes) {
        Map<Recipe, Map<RecipeChoice, Integer>> allRecipes = new HashMap<>();
        Map<RecipeChoice, Integer> ingredients = new HashMap<>();

        for (Recipe recipe : recipes) {
            if (recipe instanceof ShapedRecipe shapedRecipe) {
                ingredients = handleShapedRecipe(shapedRecipe);
            }

            else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
                ingredients = handleShapelessRecipe(shapelessRecipe);
            }

            allRecipes.put(recipe, ingredients);
        }

        return allRecipes;
    }

    private static Map<RecipeChoice, Integer> handleShapedRecipe(ShapedRecipe shapedRecipe) {
        Map<RecipeChoice, Integer> ingredients = new HashMap<>();

        String[] shape = shapedRecipe.getShape();
        Map<Character, RecipeChoice> choices = shapedRecipe.getChoiceMap();

        for (String row : shape) {
            for (char key : row.toCharArray()) {
                RecipeChoice recipeChoice = choices.get(key);

                if (recipeChoice != null) {
                    if (ingredients.containsKey(recipeChoice)) {
                        ingredients.replace(recipeChoice, ingredients.get(recipeChoice) + 1);
                    }

                    else {
                        ingredients.put(recipeChoice, 1);
                    }
                }
            }
        }

        return ingredients;
    }

    private static Map<RecipeChoice, Integer> handleShapelessRecipe(ShapelessRecipe shapelessRecipe) {
        Map<RecipeChoice, Integer> ingredients = new HashMap<>();

        List<RecipeChoice> choices = shapelessRecipe.getChoiceList();

        for (RecipeChoice recipeChoice : choices) {
            if (recipeChoice != null) {
                if (ingredients.containsKey(recipeChoice)) {
                    ingredients.replace(recipeChoice, ingredients.get(recipeChoice) + 1);
                }

                else {
                    ingredients.put(recipeChoice, 1);
                }
            }
        }

        return ingredients;
    }
}
