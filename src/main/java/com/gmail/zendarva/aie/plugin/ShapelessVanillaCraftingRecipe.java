package com.gmail.zendarva.aie.plugin;

import com.gmail.zendarva.aie.api.IIngredient;
import com.gmail.zendarva.aie.domain.AEIItemStack;
import net.minecraft.item.crafting.ShapelessRecipe;

import java.util.LinkedList;
import java.util.List;

public class ShapelessVanillaCraftingRecipe extends VanillaCraftingRecipe {

    private final ShapelessRecipe recipe;
    List<AEIItemStack> output;
    List<List<AEIItemStack>> input;

    public ShapelessVanillaCraftingRecipe(ShapelessRecipe recipe){

        this.recipe = recipe;
        output = new LinkedList<>();
        output.add(new AEIItemStack(recipe.getRecipeOutput()));


    }

    @Override
    public List<AEIItemStack> getOutput() {
        return output;
    }

    @Override
    public List<List<AEIItemStack>> getInput() {
        return null;
    }
}
