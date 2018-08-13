package com.gmail.zendarva.aie.plugin;

import com.gmail.zendarva.aie.impl.AEIRecipeManager;
import com.gmail.zendarva.aie.listenerdefinitions.RecipeLoadListener;
import com.gmail.zendarva.aie.plugin.crafting.VanillaCraftingCategory;
import com.gmail.zendarva.aie.plugin.crafting.VanillaCraftingRecipe;
import com.gmail.zendarva.aie.plugin.crafting.VanillaShapedCraftingRecipe;
import com.gmail.zendarva.aie.plugin.crafting.VanillaShapelessCraftingRecipe;
import com.gmail.zendarva.aie.plugin.furnace.VanillaFurnaceCategory;
import com.gmail.zendarva.aie.plugin.furnace.VanillaFurnaceRecipe;
import net.minecraft.item.crafting.*;

import java.util.LinkedList;
import java.util.List;

public class VanillaPlugin implements RecipeLoadListener {
    @Override
    public void recipesLoaded(RecipeManager recipeManager) {
        List<VanillaCraftingRecipe> recipes = new LinkedList<>();
        List<VanillaFurnaceRecipe> furnaceRecipes = new LinkedList<>();
        AEIRecipeManager.instance().addDisplayAdapter(new VanillaCraftingCategory());
        AEIRecipeManager.instance().addDisplayAdapter(new VanillaFurnaceCategory());

        for (IRecipe recipe : recipeManager.getRecipes()) {
            if (recipe instanceof ShapelessRecipe){
                recipes.add(new VanillaShapelessCraftingRecipe((ShapelessRecipe) recipe));
            }
            if (recipe instanceof ShapedRecipe){
                recipes.add(new VanillaShapedCraftingRecipe((ShapedRecipe) recipe));
            }
            if (recipe instanceof FurnaceRecipe){
                furnaceRecipes.add(new VanillaFurnaceRecipe((FurnaceRecipe) recipe));
            }
        }

        AEIRecipeManager.instance().addRecipe("vanilla",recipes);
        AEIRecipeManager.instance().addRecipe("furnace",furnaceRecipes);
    }
}
