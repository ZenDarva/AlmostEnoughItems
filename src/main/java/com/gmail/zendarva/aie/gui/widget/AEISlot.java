package com.gmail.zendarva.aie.gui.widget;

import com.gmail.zendarva.aie.gui.AEIRenderHelper;
import com.gmail.zendarva.aie.network.CheatPacket;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by James on 7/28/2018.
 */
public class AEISlot extends Control {
    private static final ResourceLocation RECIPE_GUI = new ResourceLocation("almostenoughitems","textures/gui/recipecontainer.png");
    private ItemStack stack;
    private boolean cheatable = false;

    public boolean isDrawBackground() {
        return drawBackground;
    }

    public void move(int x, int y){
        rect.move(x+rect.x,rect.y+y);//Why the fuck?
    }

    public void setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
    }

    private boolean drawBackground = false;
    private Point backgroundUV = new Point(0,222);

    public AEISlot(int x, int y) {
        super(x, y, 18, 18);
        this.onClick=this::onClick;
    }

    public ItemStack getStack(){
        return stack;
    }

    public void setStack(ItemStack stack ){
        this.stack = stack;
    }

    @Override
    public void draw() {
        if (drawBackground) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(RECIPE_GUI);
            drawTexturedModalRect(rect.x-1, rect.y-1, backgroundUV.x, backgroundUV.y, rect.width, rect.height);
        }
        if (stack == null)
            return;
        RenderHelper.enableGUIStandardItemLighting();
        drawStack(rect.x,rect.y);
        if (isHighlighted())
            drawTooltip();
    }

    private void drawTooltip() {
        List<String> toolTip = getTooltip();
        toolTip.add(getMod());
        Point mouse = AEIRenderHelper.getMouseLoc();
        AEIRenderHelper.addToolTip(toolTip,mouse.x,mouse.y);
    }

    private boolean onClick(int button) {
        int level = 0;
        if (Minecraft.getMinecraft().getIntegratedServer() != null) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            level = Minecraft.getMinecraft().getIntegratedServer().getPermissionLevel(player.getGameProfile());
        }
        if (level >1 && this.cheatable){
            if (stack != null && ! stack.isEmpty()) {
                ItemStack cheatyStack = stack.copy();
                if (button == 0)
                    cheatyStack.setCount(1);
                if (button == 1){
                    cheatyStack.setCount(cheatyStack.getMaxStackSize());
                }
                Minecraft.getMinecraft().getConnection().sendPacket(new CheatPacket(cheatyStack,Minecraft.getMinecraft().player.getUniqueID()));
                return true;
            }
        }
        return false;
    }


    private void drawStack(int x, int y) {
        GuiContainer gui = AEIRenderHelper.getOverlayedGui();
        gui.zLevel = 200.0F;
        gui.itemRender.zLevel = 200.0F;
        gui.itemRender.renderItemAndEffectIntoGUI(stack,x,y);
        gui.itemRender.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, stack, x, y - (gui.draggedStack.isEmpty() ? 0 : 8), "");
        gui.zLevel = 0.0F;
        gui.itemRender.zLevel = 0.0F;
    }

    public String getMod() {
        if (stack != null) {
            ResourceLocation location = Item.REGISTRY.getNameForObject(stack.getItem());
            return location.getNamespace();
        }
        return "";
    }

    private List<String> getTooltip() {
        Minecraft mc = Minecraft.getMinecraft();
        List unlocalizedTooltip = stack.getTooltip(mc.player, mc.gameSettings.advancedItemTooltips? ITooltipFlag.TooltipFlags.ADVANCED: ITooltipFlag.TooltipFlags.NORMAL);
        ArrayList toolTip = Lists.newArrayList();
        Iterator var4 = unlocalizedTooltip.iterator();

        while(var4.hasNext()) {
            ITextComponent unlocalizedTip = (ITextComponent)var4.next();
            toolTip.add(unlocalizedTip.createCopy().getFormattedText());
        }

        return toolTip;
    }

    public boolean isCheatable() {
        return cheatable;
    }

    public void setCheatable(boolean cheatable) {
        this.cheatable = cheatable;
    }

    private void drawTexturedModalRect(int p_drawTexturedModalRect_1_, int p_drawTexturedModalRect_2_, int p_drawTexturedModalRect_3_, int p_drawTexturedModalRect_4_, int p_drawTexturedModalRect_5_, int p_drawTexturedModalRect_6_) {
        float lvt_7_1_ = 0.00390625F;
        float lvt_8_1_ = 0.00390625F;
        Tessellator lvt_9_1_ = Tessellator.getInstance();
        BufferBuilder lvt_10_1_ = lvt_9_1_.getBuffer();
        lvt_10_1_.begin(7, DefaultVertexFormats.POSITION_TEX);
        lvt_10_1_.pos((double)(p_drawTexturedModalRect_1_ + 0), (double)(p_drawTexturedModalRect_2_ + p_drawTexturedModalRect_6_), (double)200).tex((double)((float)(p_drawTexturedModalRect_3_ + 0) * 0.00390625F), (double)((float)(p_drawTexturedModalRect_4_ + p_drawTexturedModalRect_6_) * 0.00390625F)).endVertex();
        lvt_10_1_.pos((double)(p_drawTexturedModalRect_1_ + p_drawTexturedModalRect_5_), (double)(p_drawTexturedModalRect_2_ + p_drawTexturedModalRect_6_), (double)200).tex((double)((float)(p_drawTexturedModalRect_3_ + p_drawTexturedModalRect_5_) * 0.00390625F), (double)((float)(p_drawTexturedModalRect_4_ + p_drawTexturedModalRect_6_) * 0.00390625F)).endVertex();
        lvt_10_1_.pos((double)(p_drawTexturedModalRect_1_ + p_drawTexturedModalRect_5_), (double)(p_drawTexturedModalRect_2_ + 0), (double)200).tex((double)((float)(p_drawTexturedModalRect_3_ + p_drawTexturedModalRect_5_) * 0.00390625F), (double)((float)(p_drawTexturedModalRect_4_ + 0) * 0.00390625F)).endVertex();
        lvt_10_1_.pos((double)(p_drawTexturedModalRect_1_ + 0), (double)(p_drawTexturedModalRect_2_ + 0), (double)200).tex((double)((float)(p_drawTexturedModalRect_3_ + 0) * 0.00390625F), (double)((float)(p_drawTexturedModalRect_4_ + 0) * 0.00390625F)).endVertex();
        lvt_9_1_.draw();
    }
}
