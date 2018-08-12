package com.gmail.zendarva.aie.plugin.furnace;

import com.gmail.zendarva.aie.api.IDisplayCategory;
import com.gmail.zendarva.aie.gui.widget.AEISlot;

import java.util.List;

public class VanillaFurnaceCategory implements IDisplayCategory<VanillaFurnaceRecipe> {
    private VanillaFurnaceRecipe recipe;

    @Override
    public String getId() {
        return "furnace";
    }

    @Override
    public String getDisplayName() {
        return "Smelting";
    }

    @Override
    public void setRecipe(VanillaFurnaceRecipe recipe) {

        this.recipe = recipe;
    }

    @Override
    public List<AEISlot> setupDisplay() {
        return null;
    }

    @Override
    public boolean canDisplay(VanillaFurnaceRecipe recipe) {
        return false;
    }
}
