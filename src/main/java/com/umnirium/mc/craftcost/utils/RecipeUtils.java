package com.umnirium.mc.craftcost.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;

import java.util.*;

public class RecipeUtils {
    public static Map<Material, Integer> ListIngredients(List<Recipe> recipes) {
        Map<Material, Integer> ingredients = new HashMap<>();

        for (Recipe recipe : recipes) {
            if (recipe instanceof ShapedRecipe shapedRecipe) {
                handleShapedRecipe(ingredients, shapedRecipe);
            }

            else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
                handleShapelessRecipe(ingredients, shapelessRecipe);
            }
        }

        return ingredients;
    }

    private static void handleShapedRecipe(Map<Material, Integer> ingredients, ShapedRecipe shapedRecipe) {
        String[] shape = shapedRecipe.getShape();
        Map<Character, RecipeChoice> choices = shapedRecipe.getChoiceMap();

        for (String row : shape) {
            for (char key : row.toCharArray()) {
                RecipeChoice recipeChoice = choices.get(key);

                if (recipeChoice instanceof RecipeChoice.MaterialChoice materialChoice) {
                    handleMaterialChoice(ingredients, materialChoice);
                }

                else if (recipeChoice instanceof RecipeChoice.ExactChoice exactChoice) {
                    handleExactChoice(ingredients, exactChoice);
                }
            }
        }
    }

    private static void handleShapelessRecipe(Map<Material, Integer> ingredients, ShapelessRecipe shapelessRecipe) {
        List<RecipeChoice> choices = shapelessRecipe.getChoiceList();

        for (RecipeChoice recipeChoice : choices) {
            if (recipeChoice instanceof RecipeChoice.MaterialChoice materialChoice) {
                handleMaterialChoice(ingredients, materialChoice);
            }

            else if (recipeChoice instanceof RecipeChoice.ExactChoice exactChoice) {
                handleExactChoice(ingredients, exactChoice);
            }
        }
    }

    public static void handleMaterialChoice(Map<Material, Integer> ingredients, RecipeChoice.MaterialChoice materialChoice) {
        List<Material> materials = materialChoice.getChoices();

        for (Material material : materials) {
            if (ingredients.containsKey(material)) {
                ingredients.replace(material, ingredients.get(material) + 1);
            }

            else {
                ingredients.put(material, 1);
            }
        }
    }

    public static void handleExactChoice(Map<Material, Integer> ingredients, RecipeChoice.ExactChoice exactChoice) {
        List<ItemStack> itemStacks = exactChoice.getChoices();

        for (ItemStack itemStack : itemStacks) {
            if (ingredients.containsKey(itemStack.getType())) {
                ingredients.replace(itemStack.getType(), ingredients.get(itemStack.getType()) + 1);
            }

            else {
                ingredients.put(itemStack.getType(), 1);
            }
        }
    }
}
