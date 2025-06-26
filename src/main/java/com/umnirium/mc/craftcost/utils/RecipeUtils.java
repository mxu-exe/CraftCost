package com.umnirium.mc.craftcost.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.umnirium.mc.craftcost.CraftCost.LOGGER;

public class RecipeUtils {
    public static List<Recipe> getRecipes(Material material) {
        Iterator<Recipe> allRecipes = Bukkit.recipeIterator();

        List<Recipe> recipes = new ArrayList<>();

        while (allRecipes.hasNext()) {
            Recipe recipe = allRecipes.next();

            if (recipe == null) {
                continue;
            }

            if (recipe.getResult().getType() == material) {
                recipes.add(recipe);
            }
        }

        return recipes;
    }

    public static void ListIngredients(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            if (recipe instanceof ShapedRecipe shapedRecipe) {
                String[] shape = shapedRecipe.getShape();
                Map<Character, RecipeChoice> choices = shapedRecipe.getChoiceMap();

                for (String row : shape) {
                    for (char key : row.toCharArray()) {
                        RecipeChoice recipeChoice = choices.get(key);

                        if (recipeChoice instanceof RecipeChoice.MaterialChoice materialChoice) {
                            List<Material> materials = materialChoice.getChoices();

                            for (Material material : materials) {
                                LOGGER.info(material.name());
                            }
                        }

                        else if (recipeChoice instanceof RecipeChoice.ExactChoice exactChoice) {
                            List<ItemStack> itemStacks = exactChoice.getChoices();

                            for (ItemStack itemStack : itemStacks) {
                                LOGGER.info(itemStack.getType().name());
                            }
                        }
                    }
                }
            }

            else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
                @NotNull List<RecipeChoice> choices = shapelessRecipe.getChoiceList();

                for (RecipeChoice recipeChoice : choices) {
                    if (recipeChoice instanceof RecipeChoice.MaterialChoice materialChoice) {
                        List<Material> materials = materialChoice.getChoices();

                        for (Material material : materials) {
                            LOGGER.info(material.name());
                        }
                    }

                    else if (recipeChoice instanceof RecipeChoice.ExactChoice exactChoice) {
                        List<ItemStack> itemStacks = exactChoice.getChoices();

                        for (ItemStack itemStack : itemStacks) {
                            LOGGER.info(itemStack.getType().name());
                        }
                    }
                }
            }
        }
    }
}
