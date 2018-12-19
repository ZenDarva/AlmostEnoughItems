package com.gmail.zendarva.aei.gui.widget;

import com.gmail.zendarva.aei.gui.AEIRenderHelper;
import com.gmail.zendarva.aei.listenerdefinitions.IMixinGuiContainer;
import com.gmail.zendarva.aei.network.CheatPacket;
import com.gmail.zendarva.aei.network.DeletePacket;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
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
            Minecraft.getInstance().getTextureManager().bindTexture(RECIPE_GUI);
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
        EntityPlayer player = Minecraft.getInstance().player;
        if (AEIRenderHelper.aeiGui.canCheat() && !(player.inventory.getItemStack().isEmpty())){
            //Delete the itemstack.
            Minecraft.getInstance().getConnection().sendPacket(new DeletePacket());
            return true;
        }
        if (!player.inventory.getItemStack().isEmpty()){
            return false;
        }

        if (AEIRenderHelper.aeiGui.canCheat() && this.cheatable){
            if (getStack() != null && ! getStack().isEmpty()) {
                ItemStack cheatyStack = getStack().copy();
                if (button == 0)
                    cheatyStack.setCount(1);
                if (button == 1){
                    cheatyStack.setCount(cheatyStack.getMaxStackSize());
                }
                Minecraft.getInstance().getConnection().sendPacket(new CheatPacket(cheatyStack));
                return true;
            }
        }
        else {
            AEIRenderHelper.recipeKeybind();
        }
        return false;
    }


    private void drawStack(int x, int y) {
        GuiContainer gui = AEIRenderHelper.getOverlayedGui();
        AEIRenderHelper.getItemRender().zLevel = 200.0F;
        AEIRenderHelper.getItemRender().renderItemAndEffectIntoGUI(getStack(),x,y);
        assert gui != null;
        if (((IMixinGuiContainer) gui).getDraggedStack().isEmpty())
            AEIRenderHelper.getItemRender().renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, getStack(), x, y - 0, "");
        else
            AEIRenderHelper.getItemRender().renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, getStack(), x, y - 8, "");
        AEIRenderHelper.getItemRender().zLevel = 0.0F;
    }

    public String getMod() {
        if (!getStack().isEmpty()) {
            ResourceLocation location = IRegistry.ITEM.getKey(getStack().getItem());
            assert location != null;
            return location.getNamespace();
        }
        return "";
    }

    private List<String> getTooltip() {
        Minecraft mc = Minecraft.getInstance();
        GuiContainer gui = AEIRenderHelper.getOverlayedGui();
        List<String> toolTip = Lists.newArrayList();
        if(gui!=null){
            toolTip = gui.getItemToolTip(getStack());
        }else{
            toolTip.add(getStack().getDisplayName().getFormattedText());
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
