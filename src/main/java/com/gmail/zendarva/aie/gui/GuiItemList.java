package com.gmail.zendarva.aie.gui;

import com.gmail.zendarva.aie.Core;
import com.gmail.zendarva.aie.api.IIngredient;
import com.gmail.zendarva.aie.gui.widget.AEISlot;
import com.gmail.zendarva.aie.gui.widget.Control;
import com.gmail.zendarva.aie.gui.widget.IFocusable;
import com.gmail.zendarva.aie.gui.widget.TextBox;
import com.gmail.zendarva.aie.util.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by James on 7/28/2018.
 */
public class GuiItemList extends Drawable {

    public static final int FOOTERSIZE = 50;
    private final GuiContainer overlayedGui;
    private static int itemOffset = 0;
    private static int nextOffset = 0;
    private static int page=0;
    private ArrayList<AEISlot> displaySlots;
    protected ArrayList<Control> controls;
    com.gmail.zendarva.aie.gui.widget.Button buttonLeft;
    com.gmail.zendarva.aie.gui.widget.Button buttonRight;
    TextBox searchBox;
    private ArrayList<IIngredient> view;

    public GuiItemList(GuiContainer overlayedGui) {
        super(calculateRect(overlayedGui));
        displaySlots = new ArrayList<>();
        controls = new ArrayList<>();
        this.overlayedGui = overlayedGui;
        view = new ArrayList<>();
        resize();

    }
    private static Rectangle calculateRect(GuiContainer overlayedGui) {
        ScaledResolution res = AEIRenderHelper.getResolution();
        int startX = (res.getScaledWidth() - overlayedGui.guiLeft) + 10 / res.getScaleFactor();
        int width = res.getScaledWidth() - startX;
        return new Rectangle(startX, 0, width, res.getScaledHeight());
    }

    protected void resize() {
        rect = calculateRect(overlayedGui);
        itemOffset = 0;
        page =0;
        buttonLeft = new com.gmail.zendarva.aie.gui.widget.Button(rect.x+5,rect.height-32,8,20,"<");
        buttonLeft.onClick= this::btnLeftClicked;
        buttonRight = new com.gmail.zendarva.aie.gui.widget.Button(rect.x + rect.width-10,rect.height-32,8,20,">");
        buttonRight.onClick=this::btnRightClicked;
        controls.clear();
        controls.add(buttonLeft);
        controls.add(buttonRight);

        searchBox = new TextBox(rect.x+rect.width/4,rect.height-32,rect.width/2,20);
        controls.add(searchBox);
        calculateSlots();
        updateView();
        fillSlots();
    }

    private void fillSlots(){
        int firstSlot = page * displaySlots.size();
        for (int i = 0; i < displaySlots.size();i++)
        {
            if (firstSlot+i < view.size()) {
                displaySlots.get(i).setIngredient(view.get(firstSlot + i));
            }
            else {
                displaySlots.get(i).setIngredient(null);
            }
        }
    }

    private void calculateSlots() {
        int x = rect.x;
        int y = rect.y;
        ScaledResolution res = AEIRenderHelper.getResolution();
        displaySlots.clear();
        int xOffset = 4;
        int yOffset = 4;
        while (true) {
            AEISlot slot = new AEISlot(x + xOffset, y + yOffset);
            xOffset += 18;
            displaySlots.add(slot);
            if (x + xOffset + 18 > res.getScaledWidth()) {
                xOffset = 4;
                yOffset += 18;
            }
            if (y + yOffset + 18 + FOOTERSIZE/res.getScaleFactor() > res.getScaledHeight()) {
                break;
            }
        }
    }

    @Override
    public void draw() {
        GlStateManager.pushMatrix();
        drawRect(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, 0x90606060);
        updateButtons();
        controls.forEach(Control::draw);
        drawSlots();
        GlStateManager.popMatrix();
    }

    private void drawSlots() {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();

        RenderHelper.enableGUIStandardItemLighting();
        for (AEISlot displaySlot : displaySlots) {
            displaySlot.draw();
        }
        GlStateManager.popMatrix();
    }

    public static void drawRect(int p_drawRect_0_, int p_drawRect_1_, int p_drawRect_2_, int p_drawRect_3_, int p_drawRect_4_) {
        int lvt_5_3_;
        if (p_drawRect_0_ < p_drawRect_2_) {
            lvt_5_3_ = p_drawRect_0_;
            p_drawRect_0_ = p_drawRect_2_;
            p_drawRect_2_ = lvt_5_3_;
        }

        if (p_drawRect_1_ < p_drawRect_3_) {
            lvt_5_3_ = p_drawRect_1_;
            p_drawRect_1_ = p_drawRect_3_;
            p_drawRect_3_ = lvt_5_3_;
        }

        float lvt_5_3_1 = (float) (p_drawRect_4_ >> 24 & 255) / 255.0F;
        float lvt_6_1_ = (float) (p_drawRect_4_ >> 16 & 255) / 255.0F;
        float lvt_7_1_ = (float) (p_drawRect_4_ >> 8 & 255) / 255.0F;
        float lvt_8_1_ = (float) (p_drawRect_4_ & 255) / 255.0F;
        Tessellator lvt_9_1_ = Tessellator.getInstance();
        BufferBuilder lvt_10_1_ = lvt_9_1_.getBuffer();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(lvt_6_1_, lvt_7_1_, lvt_8_1_, lvt_5_3_1);
        lvt_10_1_.begin(7, DefaultVertexFormats.POSITION);
        lvt_10_1_.pos((double) p_drawRect_0_, (double) p_drawRect_3_, 0.0D).endVertex();
        lvt_10_1_.pos((double) p_drawRect_2_, (double) p_drawRect_3_, 0.0D).endVertex();
        lvt_10_1_.pos((double) p_drawRect_2_, (double) p_drawRect_1_, 0.0D).endVertex();
        lvt_10_1_.pos((double) p_drawRect_0_, (double) p_drawRect_1_, 0.0D).endVertex();
        lvt_9_1_.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
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
            for (IIngredient iIngredient : Core.ingredientList) {
                if (modText!= null){
                    if (iIngredient.getMod().contains(modText)){
                        view.add(iIngredient);
                    }
                }
                else{
                    view.add(iIngredient);
                }
            }
        }
        else {
           for (IIngredient iIngredient : Core.ingredientList) {
                if (iIngredient.getName().toLowerCase().contains(searchText))
                    if (modText!= null){
                        if (iIngredient.getMod().contains(modText)){
                            view.add(iIngredient);
                        }
                    }
                    else{
                        view.add(iIngredient);
                    }
            }
        }
        itemOffset=0;
        page=0;
        fillSlots();
    }
}
