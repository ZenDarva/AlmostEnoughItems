package com.gmail.zendarva.aie.plugin;

import com.gmail.zendarva.aie.api.IDisplayCategory;
import com.gmail.zendarva.aie.api.IRecipe;

public class CraftingDisplayCategory implements IDisplayCategory<VanillaCraftingRecipe> {
    @Override
    public String getId() {
        return "vanilla";
    }

    @Override
    public void setRecipe(VanillaCraftingRecipe recipe) {

    }


    @Override
    public void setupDisplay() {

    }

    @Override
    public boolean canDisplay(IRecipe recipe) {
        return recipe instanceof VanillaCraftingRecipe;
    }
}
