package com.gmail.zendarva.aie.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

/**
 * Created by James on 7/28/2018.
 */
public class AEISlot extends Drawable {

    private ItemStack stack;

    public AEISlot(int x, int y) {
        super(x, y, 18, 18);
    }

    public ItemStack getItemstack(){
        return stack;
    }

    public void setItemstack(ItemStack stack ){

        this.stack = stack;
    }

    @Override
    public void draw() {
        AEIRenderHelper.getItemRender().zLevel = 200.0F;
        AEIRenderHelper.getItemRender().renderItemAndEffectIntoGUI(stack, rect.x, rect.y);
        AEIRenderHelper.getItemRender().zLevel = 0.0F;

        if (isHighlighted())
            System.out.println("Would draw tooltip.");

    }
}
