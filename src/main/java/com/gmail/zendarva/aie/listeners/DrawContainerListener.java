package com.gmail.zendarva.aie.listeners;

import com.gmail.zendarva.aie.Core;
import com.gmail.zendarva.aie.api.IIngredient;
import com.gmail.zendarva.aie.domain.AEIItemStack;
import com.gmail.zendarva.aie.gui.AEIRenderHelper;
import com.gmail.zendarva.aie.listenerdefinitions.DrawContainer;
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
public class DrawContainerListener implements DrawContainer {
    ResourceLocation location = new ResourceLocation("almostenoughitems", "menu_background");
    private int itemOffset = 0;
    private int nextOffset;

    @Override
    public void draw(int x, int y, float dunno, GuiContainer gui) {
        AEIRenderHelper.setMouseLoc(x,y);

        AEIRenderHelper.drawAEI(gui);

//        GlStateManager.disableRescaleNormal();
//        RenderHelper.disableStandardItemLighting();
//        GlStateManager.disableLighting();
//        GlStateManager.disableDepth();
//        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
//        int startX = resolution.getScaledWidth() - (gui.guiLeft);
//        int width = resolution.getScaledWidth();
//
//        GlStateManager.pushMatrix();
//
//        drawRect(startX, 0, width, resolution.getScaledHeight(), 0x90606060);
//        drawItems(gui, startX, 0);
//
//        GlStateManager.popMatrix();


    }




}
