package com.gmail.zendarva.aie.plugin;

import com.gmail.zendarva.aie.api.IAEIPlugin;
import com.gmail.zendarva.aie.impl.AEIRecipeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipe;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 8/5/2018.
 */
public class VanillaPlugin implements IAEIPlugin {
    AEIRecipeManager AEIrecipeManager = AEIRecipeManager.instance();
    @Override
    public void register() {
        List<VanillaCraftingRecipe> recipes = new LinkedList<>();
        AEIrecipeManager.addDisplayAdapter(new CraftingDisplayCategory());

        for (IRecipe recipe : AEIrecipeManager.recipeManager.getRecipes()) {
            if (recipe instanceof ShapelessRecipe)
                recipes.add(new ShapelessVanillaCraftingRecipe((ShapelessRecipe) recipe));
        }

        AEIrecipeManager.addRecipe("vanilla",recipes);

    }
}
