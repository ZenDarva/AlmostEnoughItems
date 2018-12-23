package com.gmail.zendarva.aei.plugin.crafting;

import com.gmail.zendarva.aei.api.IDisplayCategory;
import com.gmail.zendarva.aei.gui.widget.AEISlot;
import com.gmail.zendarva.aei.gui.widget.Control;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

import java.util.LinkedList;
import java.util.List;

public class VanillaCraftingCategory implements IDisplayCategory<VanillaCraftingRecipe> {
    MainWindow mainWindow = Minecraft.getInstance().mainWindow;
    private VanillaCraftingRecipe recipe;

    @Override
    public String getId() {
        return "vanilla";
    }

    @Override
    public String getDisplayName() {
        return new TextComponentString(I18n.format("text.aei.crafting")).getFormattedText();
    }

    @Override
    public void setRecipe(VanillaCraftingRecipe recipe) {

        this.recipe = recipe;
    }

    @Override
    public List<AEISlot> setupDisplay() {
        List<AEISlot> slots = new LinkedList<>();
        int count =0;
        List<List<ItemStack>> input = recipe.getInput();
        for (int y = 0; y<3;y++){
            for (int x = 0;x<3;x++) {
                AEISlot slot = new AEISlot(20+x*18,72+y*18);
                slot.setDrawBackground(true);
                slots.add(slot);
                count++;
            }

        }
        for (int i = 0; i < input.size(); i++) {
            if (recipe instanceof VanillaShapedCraftingRecipe){
                if (!input.get(i).isEmpty())
                    slots.get(getSlotWithSize(i)).setStackList(input.get(i));
            }
            else
                if (!input.get(i).isEmpty())
                    slots.get(i).setStackList(input.get(i));
        }
        AEISlot slot = new AEISlot(130,90);

        slot.setDrawBackground(true);
        slot.setStack(recipe.getOutput().get(0).copy());
        slots.add(slot);
        return slots;
    }

    @Override
    public boolean canDisplay(VanillaCraftingRecipe recipe) {
        return false;
    }

    @Override
    public void drawExtras() {

    }

    @Override
    public void addWidget(List<Control> controls) {

    }

    private int getSlotWithSize( int num){
        if (recipe.getWidth() == 1){
            if (num == 1)
                return 3;
            if (num == 2)
                return  6;
        }

        if (recipe.getWidth() == 2){
            if (num == 2)
                return 3;
            if (num == 3)
                return 4;
            if (num == 4)
                return 6;
            if (num ==5)
                return 7;

        }
        return num;
    }
}
