package com.gmail.zendarva.aie.gui.widget;

import com.gmail.zendarva.aie.api.IIngredient;
import com.gmail.zendarva.aie.domain.AEIItemStack;
import com.gmail.zendarva.aie.gui.AEIRenderHelper;
import com.gmail.zendarva.aie.network.CheatPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.List;

/**
 * Created by James on 7/28/2018.
 */
public class AEISlot extends Control {

    private IIngredient stack;

    public AEISlot(int x, int y) {
        super(x, y, 18, 18);
        this.onClick=this::onClick;
    }

    public IIngredient getIngredient(){
        return stack;
    }

    public void setIngredient(IIngredient stack ){
        this.stack = stack;
    }

    @Override
    public void draw() {
        if (stack == null)
            return;
        RenderHelper.enableGUIStandardItemLighting();
        stack.draw(rect.x,rect.y);
        if (isHighlighted())
            drawTooltip();
    }

    private void drawTooltip() {
        List<String> toolTip = stack.getTooltip();
        toolTip.add(stack.getMod());
        Point mouse = AEIRenderHelper.getMouseLoc();
        AEIRenderHelper.addToolTip(toolTip,mouse.x,mouse.y);
    }

    private boolean onClick(int button) {
        int level = 0;
        if (Minecraft.getMinecraft().getIntegratedServer() != null)
            level = Minecraft.getMinecraft().getIntegratedServer().getOpPermissionLevel();
        if (level >1){
            if (stack instanceof AEIItemStack) {
                ItemStack cheatyStack = ((AEIItemStack) stack).getItemStack();
                if (button == 0)
                    cheatyStack.setCount(1);
                if (button == 1){
                    cheatyStack.setCount(cheatyStack.getMaxStackSize());
                }
                Minecraft.getMinecraft().getConnection().sendPacket(new CheatPacket(cheatyStack,Minecraft.getMinecraft().player.getUniqueID()));
                //Minecraft.getMinecraft().player.inventory.addItemStackToInventory(cheatyStack);
                return true;
            }
        }
        return false;
    }

}
