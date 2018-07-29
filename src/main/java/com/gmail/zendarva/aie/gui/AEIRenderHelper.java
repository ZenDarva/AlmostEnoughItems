package com.gmail.zendarva.aie.gui;

import com.gmail.zendarva.aie.api.IIngredient;
import com.gmail.zendarva.aie.gui.widget.Control;
import com.gmail.zendarva.aie.util.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.ItemRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by James on 7/28/2018.
 */
public class AEIRenderHelper {
    static Point mouseLoc;
    static GuiItemList aeiGui;
    static GuiContainer overlayedGUI;
    static List<TooltipData> tooltipsToRender = new ArrayList<>();
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
        overlayedGUI=overlayedGui;
        if (aeiGui ==  null){
            aeiGui = new GuiItemList(overlayedGui);
        }
        aeiGui.draw();
        renderTooltips();
    }

    public static void resize() {
        if (aeiGui !=  null){
         aeiGui.resize();
        }
    }

    public static ItemRenderer getItemRender(){
        return Minecraft.getMinecraft().getRenderItem();
    }

    public static GuiContainer getOverlayedGui() {
        return overlayedGUI;
    }

    public static List<String> getTooltip(IIngredient ingredient){
        return null;
    }

    public static void addToolTip(List<String> text, int x, int y) {
        tooltipsToRender.add(new TooltipData(text,x,y));
    }


    private static void renderTooltips(){
        for (TooltipData tooltipData : tooltipsToRender) {
            getOverlayedGui().drawHoveringText(tooltipData.text,tooltipData.x,tooltipData.y);
        }
        tooltipsToRender.clear();
    }

    public static boolean mouseClick(int x, int y, int button) {
        for (Control control : aeiGui.controls) {
            if (control.isHighlighted() && control.isEnabled())
                return control.onClick.apply(button);
        }
        return false;
    }

    private static class TooltipData{

        private final List<String> text;
        private final int x;
        private final int y;

        public TooltipData(List<String> text, int x, int y){
            this.text = text;
            this.x = x;
            this.y = y;
        }
    }
}
