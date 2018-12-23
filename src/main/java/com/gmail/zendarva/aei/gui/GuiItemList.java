package com.gmail.zendarva.aei.gui;

import com.gmail.zendarva.aei.ClientListener;
import com.gmail.zendarva.aei.Core;
import com.gmail.zendarva.aei.gui.widget.AEISlot;
import com.gmail.zendarva.aei.gui.widget.Button;
import com.gmail.zendarva.aei.gui.widget.Control;
import com.gmail.zendarva.aei.gui.widget.TextBox;
import com.gmail.zendarva.aei.listenerdefinitions.IMixinGuiContainer;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ContainerGui;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.awt.*;
import java.util.ArrayList;

public class GuiItemList extends Drawable {

    public static final int FOOTERSIZE = 70;
    private ContainerGui overlayedGui;
    private static int page=0;
    private ArrayList<AEISlot> displaySlots;
    protected ArrayList<Control> controls;
    private boolean needsResize = false;
    com.gmail.zendarva.aei.gui.widget.Button buttonLeft;
    com.gmail.zendarva.aei.gui.widget.Button buttonRight;
    Button buttonCheating;
    TextBox searchBox;
    private ArrayList<ItemStack> view;
    private Control lastHovered;
    protected boolean visible = true;
    private int oldGuiLeft = 0;
    private boolean cheatMode = false;

    public GuiItemList(ContainerGui overlayedGui) {
        super(calculateRect(overlayedGui));
        displaySlots = new ArrayList<>();
        controls = new ArrayList<>();
        this.overlayedGui = overlayedGui;
        view = new ArrayList<>();
        resize();

    }

    public boolean canCheat(){
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (cheatMode)
        {
            if (!player.allowsPermissionLevel(1)) {
                cheatClicked(0);
                return false;
            }
            return true;
        }
        return false;
    }

    private static Rectangle calculateRect(ContainerGui overlayedGui) {
        Window res = AEIRenderHelper.getResolution();
        int startX = (((IMixinGuiContainer)overlayedGui).getGuiLeft() + ((IMixinGuiContainer)overlayedGui).getXSize()) + 10;
        int width = res.getScaledWidth() - startX;
        return new Rectangle(startX, 0, width, res.getScaledHeight());
    }

    protected void resize() {
        Window res = AEIRenderHelper.getResolution();

        if (overlayedGui!= MinecraftClient.getInstance().currentGui){
            if (MinecraftClient.getInstance().currentGui instanceof ContainerGui){
                overlayedGui= (ContainerGui) MinecraftClient.getInstance().currentGui;

            }
            else{
                needsResize=true;
                return;
            }
        }
        oldGuiLeft=((IMixinGuiContainer)overlayedGui).getGuiLeft();
        rect = calculateRect(overlayedGui);
        page =0;
        buttonLeft = new com.gmail.zendarva.aei.gui.widget.Button(rect.x+5, (int) (rect.height-Math.max(32/res.getGuiScaleFactor(),22)),8,20,"<");
        buttonLeft.onClick= this::btnLeftClicked;
        buttonRight = new com.gmail.zendarva.aei.gui.widget.Button(rect.x + rect.width-10, (int) (rect.height-Math.max(32/res.getGuiScaleFactor(),22)),8,20,">");
        buttonRight.onClick=this::btnRightClicked;
        controls.clear();
        controls.add(buttonLeft);
        controls.add(buttonRight);
        String savedText = "";
        if (searchBox != null){
            savedText = searchBox.getText();
        }
        searchBox = new TextBox(rect.x+rect.width/4, (int) (rect.height-Math.max(32/res.getGuiScaleFactor(),22)),rect.width/2,20);
        searchBox.setText(savedText);
        controls.add(searchBox);
        buttonCheating = new Button(5,5,45,20,getCheatModeText());
        buttonCheating.onClick = this::cheatClicked;
        controls.add(buttonCheating);
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
        Window res = AEIRenderHelper.getResolution();
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
        if (oldGuiLeft != ((IMixinGuiContainer)overlayedGui).getGuiLeft())
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

    public boolean cheatClicked(int button){
        if (button == 0) {
            cheatMode = !cheatMode;

            buttonCheating.setString(getCheatModeText());
            return true;
        }
        return false;
    }

    private String getCheatModeText(){
        if (cheatMode){
            return new StringTextComponent(I18n.translate("text.aei.cheat")).getFormattedText();
        }
        return new StringTextComponent(I18n.translate("text.aei.nocheat")).getFormattedText();
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
            for (ItemStack stack : ClientListener.stackList) {
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
           for (ItemStack stack : ClientListener.stackList) {
                if (stack.getItem().getTextComponent().getString().toLowerCase().contains(searchText))
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
            Identifier location = Registry.ITEM.getId(stack.getItem());
            return location.getNamespace();
        }
        return "";
    }
}
