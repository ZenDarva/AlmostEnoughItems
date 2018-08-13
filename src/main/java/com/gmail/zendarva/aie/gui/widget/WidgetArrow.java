package com.gmail.zendarva.aie.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class WidgetArrow extends Control {
    private static final ResourceLocation RECIPE_GUI = new ResourceLocation("almostenoughitems","textures/gui/recipecontainer.png");
    private int progress = 0;
    private int updateTick =0;

    public WidgetArrow(int x, int y) {
        super(x, y, 22, 18);
    }

    @Override
    public void draw() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(RECIPE_GUI);
        this.drawTexturedModalRect(rect.x,rect.y,18,222,22,18);
        int width = (int)((progress/10f) * 22);
        this.drawTexturedModalRect(rect.x-1,rect.y-1,40,222,width,18);
    }

    @Override
    public void tick() {
        updateTick++;
        if (updateTick >=20){
            updateTick=0;

            progress++;
            if (progress > 10)
                progress = 0;
        }
    }
}
