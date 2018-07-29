package com.gmail.zendarva.aie.listeners;

import com.gmail.zendarva.aie.Core;
import com.gmail.zendarva.aie.api.IIngredient;
import com.gmail.zendarva.aie.domain.AEIItemStack;
import com.gmail.zendarva.aie.gui.AEIRenderHelper;
import com.gmail.zendarva.aie.listenerdefinitions.DrawContainer;
import com.gmail.zendarva.aie.listenerdefinitions.GuiCickListener;
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
public class DrawContainerListener implements DrawContainer, GuiCickListener {
    @Override
    public void draw(int x, int y, float dunno, GuiContainer gui) {
        AEIRenderHelper.setMouseLoc(x,y);
        AEIRenderHelper.drawAEI(gui);
    }

    @Override
    public boolean onClick(int x,int y, int button) {
        return AEIRenderHelper.mouseClick(x,y, button);
    }
}
