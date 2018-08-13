package com.gmail.zendarva.aie.plugin.furnace;

import com.gmail.zendarva.aie.api.IDisplayCategory;
import com.gmail.zendarva.aie.gui.widget.AEISlot;
import net.minecraft.block.BlockFurnace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        inputSlot.setStackList(recipe.getInput().get(0));
        inputSlot.setDrawBackground(true);

        AEISlot outputSlot = new AEISlot(100, 70);
        outputSlot.setStackList(recipe.getOutput());
        inputSlot.setDrawBackground(true);

        AEISlot fuelSlot = new AEISlot(100, 130);
        fuelSlot.setStackList(getFuel());
        inputSlot.setDrawBackground(true);

        slots.add(inputSlot);
        slots.add(outputSlot);
        slots.add(fuelSlot);
        return slots;
    }

    @Override
    public boolean canDisplay(VanillaFurnaceRecipe recipe) {
        return false;
    }

    private List<ItemStack> getFuel(){
        return TileEntityFurnace.getBurnTimes().keySet().stream().map(Item::getDefaultInstance).collect(Collectors.toList());
    }
}
