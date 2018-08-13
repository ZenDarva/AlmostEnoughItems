package com.gmail.zendarva.aie.plugin.furnace;

import com.gmail.zendarva.aie.api.IDisplayCategory;
import com.gmail.zendarva.aie.gui.widget.AEISlot;

import java.util.LinkedList;
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
        List<AEISlot> slots = new LinkedList<>();
        AEISlot inputSlot = new AEISlot(100,100);
        inputSlot.setStack(recipe.getInput().get(0).get(0));

        AEISlot outputSlot = new AEISlot(100, 70);
        outputSlot.setStack(recipe.getOutput().get(0));

        slots.add(inputSlot);
        slots.add(outputSlot);
        return slots;
    }

    @Override
    public boolean canDisplay(VanillaFurnaceRecipe recipe) {
        return false;
    }
}
