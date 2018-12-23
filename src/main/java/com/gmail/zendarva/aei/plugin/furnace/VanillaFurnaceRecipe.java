package com.gmail.zendarva.aei.plugin.furnace;

import com.gmail.zendarva.aei.api.IRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;

import java.util.LinkedList;
import java.util.List;

public class VanillaFurnaceRecipe {//implements IRecipe<ItemStack> {
    // STUB - 1.14 TODO
    /*
    private final FurnaceRecipe recipe;

    @Override
    public String getId() {
        return "furnace";
    }

    public VanillaFurnaceRecipe(FurnaceRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public List<ItemStack> getOutput() {
        List<ItemStack> output = new LinkedList<>();
        output.add(recipe.getRecipeOutput().copy());
        return output;
    }

    @Override
    public List<List<ItemStack>> getInput() {
        List<List<ItemStack>> input = new LinkedList<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            List<ItemStack> ingredients = new LinkedList<>();
            for (ItemStack matchingStack : ingredient.getMatchingStacks()) {
                ingredients.add(matchingStack);
            }
            input.add(ingredients);
        }
        return input;
    }*/
}
