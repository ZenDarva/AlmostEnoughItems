package com.gmail.zendarva.aie.gui;

import com.gmail.zendarva.aie.Core;
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

    private final GuiContainer overlayedGui;
    private static int itemOffset = 0;
    private static int nextOffset = 0;
    private static int page=0;
    private ArrayList<AEISlot> displaySlots;

    private static Rectangle calculateRect(GuiContainer overlayedGui) {
        ScaledResolution res = AEIRenderHelper.getResolution();
        int startX = (res.getScaledWidth() - overlayedGui.guiLeft) + 10 / res.getScaleFactor();
        int width = res.getScaledWidth() - startX;
        return new Rectangle(startX, 0, width, res.getScaledHeight());
    }

    public GuiItemList(GuiContainer overlayedGui) {
        super(calculateRect(overlayedGui));
        displaySlots = new ArrayList<>();
        calculateSlots();
        fillSlots();
        this.overlayedGui = overlayedGui;

    }

    protected void resize() {
        rect = calculateRect(overlayedGui);
        fillSlots();
        calculateSlots();
        itemOffset = 0;
    }

    private void fillSlots(){
        int firstSlot = page * displaySlots.size();
        for (int i = 0; i < displaySlots.size();i++)
        {
            if (firstSlot+i < Core.ingredientList.size()) {
                displaySlots.get(i).setItemstack(Core.ingredientList.get(firstSlot + i));
            }
            else
                break;
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
            if (y + yOffset + 18 + 50 > res.getScaledHeight()) {
                break;
            }
        }
    }

    @Override
    public void draw() {
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.pushMatrix();
        drawRect(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, 0x90606060);
        drawSlots();

        GlStateManager.popMatrix();

    }

    private void drawSlots() {
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
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

}
