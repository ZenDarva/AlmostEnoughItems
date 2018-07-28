package com.gmail.zendarva.aie.gui;

import com.gmail.zendarva.aie.util.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.awt.*;

/**
 * Created by James on 7/28/2018.
 */
public class AEIRenderHelper {
    static Point mouseLoc;
    static GuiItemList aeiGui;

    public static void setMouseLoc(int x, int y){
        mouseLoc = new Point(x, y);
    }

    public static Point getMouseLoc(){
        return mouseLoc;
    }

    public static ScaledResolution getResolution(){
        return new ScaledResolution(Minecraft.getMinecraft());
    }

    public static void drawAEI(GuiContainer overlayedGui){
        if (aeiGui ==  null){
            aeiGui = new GuiItemList(overlayedGui);
        }
        aeiGui.draw();
    }

    public static void resize() {
        if (aeiGui !=  null){
         aeiGui.resize();
        }
    }
}
