package com.gmail.zendarva.aei.plugin.furnace;

import com.gmail.zendarva.aei.api.IDisplayCategory;
import com.gmail.zendarva.aei.gui.widget.AEISlot;
import com.gmail.zendarva.aei.gui.widget.Control;
import com.gmail.zendarva.aei.gui.widget.WidgetArrow;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.text.TextComponentString;

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
        return new TextComponentString(I18n.format("text.aei.smelting")).getFormattedText();
    }

    @Override
    public void setRecipe(VanillaFurnaceRecipe recipe) {

        this.recipe = recipe;
    }

    @Override
    public List<AEISlot> setupDisplay() {
        List<AEISlot> slots = new LinkedList<>();
        AEISlot inputSlot = new AEISlot(50,100);
        inputSlot.setStackList(recipe.getInput().get(0));
        inputSlot.setDrawBackground(true);

        AEISlot outputSlot = new AEISlot(120, 100);
        outputSlot.setStackList(recipe.getOutput());
        outputSlot.setDrawBackground(true);

        AEISlot fuelSlot = new AEISlot(85, 130);
        fuelSlot.setStackList(getFuel());
        fuelSlot.setDrawBackground(true);
        //fuelSlot.setExtraTooltip("Fuel");
        fuelSlot.setExtraTooltip(I18n.format("text.aei.fuel"));

        slots.add(inputSlot);
        slots.add(outputSlot);
        slots.add(fuelSlot);
        return slots;
    }

    @Override
    public boolean canDisplay(VanillaFurnaceRecipe recipe) {
        return false;
    }

    @Override
    public void drawExtras() {

    }

    @Override
    public void addWidget(List<Control> controls) {
        WidgetArrow wa = new WidgetArrow(80,100);
        controls.add(wa);
    }

    private List<ItemStack> getFuel(){
        return TileEntityFurnace.getBurnTimes().keySet().stream().map(Item::getDefaultInstance).collect(Collectors.toList());
    }
}
