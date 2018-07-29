package com.gmail.zendarva.aie.gui;

import com.gmail.zendarva.aie.api.IIngredient;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.awt.*;
import java.util.List;

/**
 * Created by James on 7/28/2018.
 */
public class AEISlot extends Drawable {

    private IIngredient stack;

    public AEISlot(int x, int y) {
        super(x, y, 18, 18);
    }

    public IIngredient getItemstack(){
        return stack;
    }

    public void setItemstack(IIngredient stack ){

        this.stack = stack;
    }

    @Override
    public void draw() {


        stack.draw(rect.x,rect.y);
        if (isHighlighted())
            drawTooltip();
    }

    private void drawTooltip() {
        List<String> toolTip = stack.getTooltip();
        Point mouse = AEIRenderHelper.getMouseLoc();
        AEIRenderHelper.addToolTip(toolTip,mouse.x,mouse.y);
    }


}
