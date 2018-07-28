package com.gmail.zendarva.aie.gui;

import com.gmail.zendarva.aie.Core;
import com.gmail.zendarva.aie.domain.AEIItemStack;
import com.gmail.zendarva.aie.util.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;

import java.awt.*;

/**
 * Created by James on 7/28/2018.
 */
public class GuiItemList extends Drawable {

    private final GuiContainer overlayedGui;
    private static int itemOffset=0;
    private static int nextOffset=0;

    private static Rectangle calculateRect(GuiContainer overlayedGui){
        ScaledResolution res = AEIRenderHelper.getResolution();
        int startX = (res.getScaledWidth() - overlayedGui.guiLeft) + 10/res.getScaleFactor();
        int width = res.getScaledWidth() - startX;
        return new Rectangle(startX,0,width,res.getScaledHeight());
    }

    public GuiItemList(GuiContainer overlayedGui){
        super(calculateRect(overlayedGui));
        this.overlayedGui = overlayedGui;
    }

    protected void resize(){
        rect = calculateRect(overlayedGui);
        itemOffset=0;
    }

    @Override
    public void draw() {
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.pushMatrix();
        drawRect(rect.x, rect.y, rect.x+rect.width, rect.y+rect.height, 0x90606060);
        drawItems(this.overlayedGui, rect.x, 0);

        GlStateManager.popMatrix();

    }

    private void drawItems(GuiContainer gui, int x, int y) {
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();

        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        int xOffset = 4;
        int yOffset = 4;
        for (int i = itemOffset; i < Core.ingredientList.size(); i++) {
            ItemStack stack = ((AEIItemStack) Core.ingredientList.get(i)).getItemStack();
            drawItemStack(gui, stack, x + xOffset, y + yOffset, "");
            xOffset += 18;
            if (x + xOffset + 18 > resolution.getScaledWidth()) {
                xOffset = 4;
                yOffset += 18;
            }
            if (y + yOffset + 18 + 50 > resolution.getScaledHeight()) {
                nextOffset = i + 1;
                break;
            }
        }
        GlStateManager.popMatrix();


    }

    private void drawItemStack(GuiContainer gui, ItemStack p_drawItemStack_1_, int p_drawItemStack_2_, int p_drawItemStack_3_, String p_drawItemStack_4_) {
        gui.zLevel = 200.0F;
        gui.itemRender.zLevel = 200.0F;
        gui.itemRender.renderItemAndEffectIntoGUI(p_drawItemStack_1_, p_drawItemStack_2_, p_drawItemStack_3_);
        gui.itemRender.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, p_drawItemStack_1_, p_drawItemStack_2_, p_drawItemStack_3_ - (gui.draggedStack.isEmpty()?0:8), p_drawItemStack_4_);
        gui.zLevel = 0.0F;
        gui.itemRender.zLevel = 0.0F;
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
