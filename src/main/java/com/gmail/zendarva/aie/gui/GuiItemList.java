package com.gmail.zendarva.aie.gui;

import com.gmail.zendarva.aie.Core;
import com.gmail.zendarva.aie.gui.widget.AEISlot;
import com.gmail.zendarva.aie.gui.widget.Control;
import com.gmail.zendarva.aie.gui.widget.TextBox;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;

public class GuiItemList extends Drawable {

    public static final int FOOTERSIZE = 70;
    private GuiContainer overlayedGui;
    private static int page=0;
    private ArrayList<AEISlot> displaySlots;
    protected ArrayList<Control> controls;
    private boolean needsResize = false;
    com.gmail.zendarva.aie.gui.widget.Button buttonLeft;
    com.gmail.zendarva.aie.gui.widget.Button buttonRight;
    TextBox searchBox;
    private ArrayList<ItemStack> view;
    private Control lastHovered;
    protected boolean visible = true;


    public GuiItemList(GuiContainer overlayedGui) {
        super(calculateRect(overlayedGui));
        displaySlots = new ArrayList<>();
        controls = new ArrayList<>();
        this.overlayedGui = overlayedGui;
        view = new ArrayList<>();
        resize();

    }
    private static Rectangle calculateRect(GuiContainer overlayedGui) {
        MainWindow res = AEIRenderHelper.getResolution();
        int startX = (int) ((res.getScaledWidth() - overlayedGui.guiLeft) + 10 / res.getGuiScaleFactor());
        int width = res.getScaledWidth() - startX;
        return new Rectangle(startX, 0, width, res.getScaledHeight());
    }

    protected void resize() {
        MainWindow res = AEIRenderHelper.getResolution();
        if (overlayedGui!= Minecraft.getMinecraft().currentScreen){
            if (Minecraft.getMinecraft().currentScreen instanceof GuiContainer){
                overlayedGui= (GuiContainer) Minecraft.getMinecraft().currentScreen;

            }
            else{
                needsResize=true;
                return;
            }
        }
        rect = calculateRect(overlayedGui);
        page =0;
        buttonLeft = new com.gmail.zendarva.aie.gui.widget.Button(rect.x+5, (int) (rect.height-Math.max(32/res.getGuiScaleFactor(),22)),8,20,"<");
        buttonLeft.onClick= this::btnLeftClicked;
        buttonRight = new com.gmail.zendarva.aie.gui.widget.Button(rect.x + rect.width-10, (int) (rect.height-Math.max(32/res.getGuiScaleFactor(),22)),8,20,">");
        buttonRight.onClick=this::btnRightClicked;
        controls.clear();
        controls.add(buttonLeft);
        controls.add(buttonRight);


        searchBox = new TextBox(rect.x+rect.width/4, (int) (rect.height-Math.max(32/res.getGuiScaleFactor(),22)),rect.width/2,20);
        controls.add(searchBox);
        calculateSlots();
        updateView();
        fillSlots();
        controls.addAll(displaySlots);
    }

    private void fillSlots(){
        int firstSlot = page * displaySlots.size();
        for (int i = 0; i < displaySlots.size();i++)
        {
            if (firstSlot+i < view.size()) {
                displaySlots.get(i).setStack(view.get(firstSlot + i));
            }
            else {
                displaySlots.get(i).setStack(ItemStack.EMPTY);
            }
        }
    }

    private void calculateSlots() {
        int x = rect.x;
        int y = rect.y;
        MainWindow res = AEIRenderHelper.getResolution();
        displaySlots.clear();
        int xOffset = 4;
        int yOffset = 4;
        while (true) {
            AEISlot slot = new AEISlot(x + xOffset, y + yOffset);
            slot.setCheatable(true);
            xOffset += 18;
            displaySlots.add(slot);
            if (x + xOffset + 18 > res.getScaledWidth()) {
                xOffset = 4;
                yOffset += 18;
            }
            if (y + yOffset + 18 + FOOTERSIZE/res.getGuiScaleFactor() > res.getScaledHeight()) {
                break;
            }
        }
    }

    @Override
    public void draw() {
        if (!visible)
            return;
        if (needsResize == true)
            resize();
        GlStateManager.pushMatrix();
        updateButtons();
        controls.forEach(Control::draw);
        GlStateManager.popMatrix();
    }
    private void updateButtons(){
        if (page == 0)
            buttonLeft.setEnabled(false);
        else
            buttonLeft.setEnabled(true);
        if (displaySlots.size() + displaySlots.size()*page >= view.size())
            buttonRight.setEnabled(false);
        else
            buttonRight.setEnabled(true);
    }


    public boolean btnRightClicked(int button){
        if (button == 0) {
            page++;
            fillSlots();
            return true;
        }
        return false;
    }
    public boolean btnLeftClicked(int button){
        if (button == 0) {
            page--;
            fillSlots();
            return true;
        }
        return false;
    }

    protected void updateView(){
        String searchText = searchBox.getText();
        String modText=null;
        if (searchText.contains("@")){
            int nextBreak = searchText.indexOf(' ',searchText.indexOf('@'));
            if (nextBreak ==0 || nextBreak==-1)
                nextBreak = searchText.length();
            modText = searchText.substring(searchText.indexOf('@'),nextBreak);
            searchText=searchText.replace(modText,"").trim();
            modText=modText.replace("@","").toLowerCase();
        }

        view.clear();
        if (searchText.equals("") || searchText==null){
            for (ItemStack stack : Core.stackList) {
                if (modText!= null){
                    if (getMod(stack).contains(modText)){
                        view.add(stack);
                    }
                }
                else{
                    view.add(stack);
                }
            }
        }
        else {
           for (ItemStack stack : Core.stackList) {
                if (stack.getItem().getName().getString().toLowerCase().contains(searchText))
                    if (modText!= null){
                        if (getMod(stack).contains(modText)){
                            view.add(stack);
                        }
                    }
                    else{
                        view.add(stack);
                    }
            }
        }
        page=0;
        fillSlots();
    }

    public void tick(){
        controls.forEach(f->f.tick());
    }
    public void setLastHovered(Control ctrl){
        lastHovered=ctrl;
    }
    public Control getLastHovered() {return lastHovered;}

    private String getMod(ItemStack stack) {
        if (stack != null && !stack.isEmpty()) {
            ResourceLocation location = Item.REGISTRY.getNameForObject(stack.getItem());
            return location.getNamespace();
        }
        return "";
    }
}
