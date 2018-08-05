package com.gmail.zendarva.aie.listeners;

import com.gmail.zendarva.aie.Core;
import com.gmail.zendarva.aie.api.IIngredient;
import com.gmail.zendarva.aie.domain.AEIItemStack;
import com.gmail.zendarva.aie.gui.AEIRenderHelper;
import com.gmail.zendarva.aie.listenerdefinitions.CharInput;
import com.gmail.zendarva.aie.listenerdefinitions.DrawContainer;
import com.gmail.zendarva.aie.listenerdefinitions.GuiCickListener;
import com.gmail.zendarva.aie.listenerdefinitions.GuiKeyDown;
import com.gmail.zendarva.aie.util.ScaledResolution;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLXARBVertexBufferObject;

/**
 * Created by James on 7/27/2018.
 */
public class DrawContainerListener implements DrawContainer, GuiCickListener, GuiKeyDown, CharInput {
    @Override
    public void draw(int x, int y, float dunno, GuiContainer gui) {
        AEIRenderHelper.setMouseLoc(x,y);
        AEIRenderHelper.drawAEI(gui);
    }

    @Override
    public boolean onClick(int x,int y, int button) {
        return AEIRenderHelper.mouseClick(x,y, button);
    }

    @Override
    public boolean keyDown(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        return AEIRenderHelper.keyDown(p_keyPressed_1_,p_keyPressed_2_,p_keyPressed_3_);
    }

    @Override
    public boolean charInput(long p_onCharEvent_1_, int p_onCharEvent_3_, int p_onCharEvent_4_) {
        return AEIRenderHelper.charInput(p_onCharEvent_1_,p_onCharEvent_3_,p_onCharEvent_4_);
    }
}
