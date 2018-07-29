package com.gmail.zendarva.aie.gui.widget;

import com.gmail.zendarva.aie.gui.AEIRenderHelper;
import com.gmail.zendarva.aie.gui.Drawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

/**
 * Created by James on 7/29/2018.
 */
public class Button extends Control {
    private final String buttonText;
    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");


    public Button(int x, int y, int width, int height, String buttonText) {
        super(x, y, width, height);
        this.buttonText = buttonText;
    }

    public Button(Rectangle rect, String buttonText) {
        super(rect);
        this.buttonText = buttonText;
    }


    @Override
    public void draw() {
        GuiContainer gui = AEIRenderHelper.getOverlayedGui();
        Minecraft lvt_4_1_ = Minecraft.getMinecraft();
        FontRenderer lvt_5_1_ = lvt_4_1_.fontRenderer;
        lvt_4_1_.getTextureManager().bindTexture(BUTTON_TEXTURES);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int hoverState =(byte)0;
        if (this.isEnabled()) {
            if (!this.isHighlighted())
                hoverState = (byte) 1;
            else
                hoverState = (byte) 2;
        }
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        gui.drawTexturedModalRect(rect.x, rect.y, 0, 46 + hoverState * 20, rect.width / 2, rect.height);
        gui.drawTexturedModalRect(rect.x + rect.width / 2, rect.y, 200 - rect.width / 2, 46 + hoverState * 20, rect.width / 2, rect.height);
        //this.mouseDragged(lvt_4_1_, p_194828_1_, p_194828_2_);
        int lvt_7_1_ = 14737632;
//        if(!this.enabled) {
//            lvt_7_1_ = 10526880;
//        } else if(this.hovered) {
//            lvt_7_1_ = 16777120;
//        }

        gui.drawCenteredString(lvt_5_1_, this.buttonText, rect.x + rect.width / 2, rect.y + (rect.height - 8) / 2, lvt_7_1_);
    }



}
