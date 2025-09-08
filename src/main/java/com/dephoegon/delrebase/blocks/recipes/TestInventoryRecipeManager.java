package com.dephoegon.delrebase.blocks.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class TestInventoryRecipeManager {

    private static final List<TestInventoryRecipe> recipes = new ArrayList<>();

    public static void addRecipe(ResourceLocation id, ItemStack output, ItemStack input, ItemStack plan) {
        recipes.add(new TestInventoryRecipe(id, output, input, plan));
    }

    public static TestInventoryRecipe findRecipe(ItemStack input, ItemStack plan) {
        for (TestInventoryRecipe recipe : recipes) {
            if (recipe.matches(input, plan)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<TestInventoryRecipe> getAllRecipes() {
        return new ArrayList<>(recipes);
    }

    public static void clearRecipes() {
        recipes.clear();
    }
}
