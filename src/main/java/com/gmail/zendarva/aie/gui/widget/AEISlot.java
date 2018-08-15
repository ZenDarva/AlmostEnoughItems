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
import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 7/28/2018.
 */
public class AEISlot extends Control {
    private static final ResourceLocation RECIPE_GUI = new ResourceLocation("almostenoughitems","textures/gui/recipecontainer.png");
    private boolean cheatable = false;
    private List<ItemStack> itemList = new LinkedList<>();
    private int itemListPointer=0;
    private int displayCounter = 0;
    public boolean isDrawBackground() {
        return drawBackground;
    }
    private String extraTooltip;


    @Override
    public void tick() {
        if (itemList.size()>1){
            displayCounter++;
        }

        if (displayCounter > 60){
            itemListPointer++;
            if (itemListPointer>= itemList.size())
                itemListPointer=0;
            displayCounter=0;
        }
    }

    public void setStackList(List<ItemStack>newItemList){
        itemList=newItemList;
        itemListPointer=0;
        displayCounter=0;
    }

    public void setExtraTooltip(String toolTip){
        extraTooltip=toolTip;
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
        if (itemList.isEmpty()){
            return ItemStack.EMPTY;
        }
        return itemList.get(itemListPointer);
    }

    public void setStack(ItemStack stack ){
        itemList.clear();
        if (stack != null)
            itemList.add(stack);
        itemListPointer =0;
    }

    @Override
    public void draw() {
        if (drawBackground) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(RECIPE_GUI);
            drawTexturedModalRect(rect.x-1, rect.y-1, backgroundUV.x, backgroundUV.y, rect.width, rect.height);
        }
        if (getStack().isEmpty())
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
            if (getStack() != null && ! getStack().isEmpty()) {
                ItemStack cheatyStack = getStack().copy();
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
        AEIRenderHelper.getItemRender().zLevel = 200.0F;
        AEIRenderHelper.getItemRender().renderItemAndEffectIntoGUI(getStack(),x,y);
        AEIRenderHelper.getItemRender().renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, getStack(), x, y - (gui.draggedStack.isEmpty() ? 0 : 8), "");
        gui.zLevel = 0.0F;
        AEIRenderHelper.getItemRender().zLevel = 0.0F;
    }

    public String getMod() {
        if (!getStack().isEmpty()) {
            ResourceLocation location = Item.REGISTRY.getNameForObject(getStack().getItem());
            return location.getNamespace();
        }
        return "";
    }

    private List<String> getTooltip() {
        Minecraft mc = Minecraft.getMinecraft();
        List unlocalizedTooltip = getStack().getTooltip(mc.player, mc.gameSettings.advancedItemTooltips? ITooltipFlag.TooltipFlags.ADVANCED: ITooltipFlag.TooltipFlags.NORMAL);
        ArrayList toolTip = Lists.newArrayList();
        Iterator var4 = unlocalizedTooltip.iterator();

        while(var4.hasNext()) {
            ITextComponent unlocalizedTip = (ITextComponent)var4.next();
            toolTip.add(unlocalizedTip.createCopy().getFormattedText());
        }

        if (extraTooltip !=null){
            toolTip.add(extraTooltip);
        }

        return toolTip;
    }

    public boolean isCheatable() {
        return cheatable;
    }

    public void setCheatable(boolean cheatable) {
        this.cheatable = cheatable;
    }


}
