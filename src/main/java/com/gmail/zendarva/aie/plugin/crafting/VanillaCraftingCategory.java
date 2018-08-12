package com.gmail.zendarva.aie.plugin.crafting;

import com.gmail.zendarva.aie.api.IDisplayCategory;
import com.gmail.zendarva.aie.gui.widget.AEISlot;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class VanillaCraftingCategory implements IDisplayCategory<VanillaCraftingRecipe> {
    MainWindow mainWindow = Minecraft.getMinecraft().mainWindow;
    private VanillaCraftingRecipe recipe;

    @Override
    public String getId() {
        return "vanilla";
    }

    @Override
    public String getDisplayName() {
        return "Crafting";
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
                    slots.get(getSlotWithSize(i)).setStack(input.get(i).get(0));
            }
            else
                if (!input.get(i).isEmpty())
                    slots.get(i).setStack(input.get(i).get(0));
        }
//        if (input.size()>count && !input.get(count).isEmpty())
//            slots.get(count).setStack(input.get(count).get(0));

        for (List<ItemStack> itemStacks : input) {

        }
//        for (int y = 0; y<recipe.getHeight();y++){
//            for (int x = 0;x<recipe.getWidth();x++) {
//
//            }
//
//        }
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
