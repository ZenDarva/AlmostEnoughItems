package com.gmail.zendarva.aie.plugin;

import com.gmail.zendarva.aie.impl.AEIRecipeManager;
import com.gmail.zendarva.aie.listenerdefinitions.RecipeLoadListener;
import com.gmail.zendarva.aie.plugin.crafting.VanillaCraftingCategory;
import com.gmail.zendarva.aie.plugin.crafting.VanillaCraftingRecipe;
import com.gmail.zendarva.aie.plugin.crafting.VanillaShapedCraftingRecipe;
import com.gmail.zendarva.aie.plugin.crafting.VanillaShapelessCraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.item.crafting.ShapelessRecipe;

import java.util.LinkedList;
import java.util.List;

public class VanillaPlugin implements RecipeLoadListener {
    @Override
    public void recipesLoaded(RecipeManager recipeManager) {
        List<VanillaCraftingRecipe> recipes = new LinkedList<>();
        AEIRecipeManager.instance().addDisplayAdapter(new VanillaCraftingCategory());

        for (IRecipe recipe : recipeManager.getRecipes()) {
            if (recipe instanceof ShapelessRecipe){
                recipes.add(new VanillaShapelessCraftingRecipe((ShapelessRecipe) recipe));
            }
            if (recipe instanceof ShapedRecipe){
                recipes.add(new VanillaShapedCraftingRecipe((ShapedRecipe) recipe));
            }
        }

        AEIRecipeManager.instance().addRecipe("vanilla",recipes);
    }
}
