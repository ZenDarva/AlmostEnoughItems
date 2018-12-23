package com.gmail.zendarva.aei.plugin;

import com.gmail.zendarva.aei.api.IAEIPlugin;
import com.gmail.zendarva.aei.impl.AEIRecipeManager;
import com.gmail.zendarva.aei.listenerdefinitions.RecipeLoadListener;
import com.gmail.zendarva.aei.plugin.crafting.VanillaCraftingCategory;
import com.gmail.zendarva.aei.plugin.crafting.VanillaCraftingRecipe;
import com.gmail.zendarva.aei.plugin.crafting.VanillaShapedCraftingRecipe;
import com.gmail.zendarva.aei.plugin.crafting.VanillaShapelessCraftingRecipe;
import com.gmail.zendarva.aei.plugin.furnace.VanillaFurnaceCategory;
import com.gmail.zendarva.aei.plugin.furnace.VanillaFurnaceRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.crafting.ShapedRecipe;
import net.minecraft.recipe.crafting.ShapelessRecipe;

import java.util.LinkedList;
import java.util.List;

public class VanillaPlugin implements IAEIPlugin {
    @Override
    public void register() {
        List<VanillaCraftingRecipe> recipes = new LinkedList<>();
        List<VanillaFurnaceRecipe> furnaceRecipes = new LinkedList<>();
        AEIRecipeManager.instance().addDisplayAdapter(new VanillaCraftingCategory());
        //AEIRecipeManager.instance().addDisplayAdapter(new VanillaFurnaceCategory());

        for (Recipe recipe : AEIRecipeManager.instance().recipeManager.values()) {
            if (recipe instanceof ShapelessRecipe){
                recipes.add(new VanillaShapelessCraftingRecipe((ShapelessRecipe) recipe));
            }
            if (recipe instanceof ShapedRecipe){
                recipes.add(new VanillaShapedCraftingRecipe((ShapedRecipe) recipe));
            }
            // TODO - 1.14 Furnace Recipe Support
            /*
            if (recipe instanceof FurnaceRecipe){
                furnaceRecipes.add(new VanillaFurnaceRecipe((FurnaceRecipe) recipe));
            }*/
        }

        AEIRecipeManager.instance().addRecipe("vanilla",recipes);
        //AEIRecipeManager.instance().addRecipe("furnace",furnaceRecipes);
    }
}
