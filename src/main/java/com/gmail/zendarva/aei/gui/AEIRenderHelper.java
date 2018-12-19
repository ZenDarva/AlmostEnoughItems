package com.gmail.zendarva.aei.gui;

import com.gmail.zendarva.aei.gui.widget.AEISlot;
import com.gmail.zendarva.aei.gui.widget.Control;
import com.gmail.zendarva.aei.gui.widget.IFocusable;
import com.gmail.zendarva.aei.impl.AEIRecipeManager;
import com.gmail.zendarva.aei.library.KeyBindManager;
import com.gmail.zendarva.aei.listenerdefinitions.IMixinGuiContainer;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by James on 7/28/2018.
 */
public class AEIRenderHelper {
    static Point mouseLoc;
    static public GuiItemList aeiGui;
    static GuiContainer overlayedGUI;
    static List<TooltipData> tooltipsToRender = new ArrayList<>();

    public static void setMouseLoc(int x, int y) {
        mouseLoc = new Point(x, y);
    }

    static public IFocusable focusedControl;

    public static Point getMouseLoc() {
        return mouseLoc;
    }

    public static MainWindow getResolution() {

        return Minecraft.getInstance().mainWindow;
    }

    public static void drawAEI(GuiContainer overlayedGui) {
        overlayedGUI = overlayedGui;
        if (aeiGui == null) {
            aeiGui = new GuiItemList(overlayedGui);
        }
        aeiGui.draw();
        renderTooltips();
    }

    public static void resize() {
        if (aeiGui != null) {
            aeiGui.resize();
        }
        if (overlayedGUI instanceof RecipeGui){
            overlayedGUI.onResize(Minecraft.getInstance(), 0,0);
        }
    }

    public static ItemRenderer getItemRender() {
        return Minecraft.getInstance().getItemRenderer();
    }

    public static FontRenderer getFontRenderer() {
        return Minecraft.getInstance().fontRenderer;
    }

    public static GuiContainer getOverlayedGui() {
        if (overlayedGUI instanceof GuiContainer)
            return overlayedGUI;
        return null;
    }

    public static void addToolTip(List<String> text, int x, int y) {
        tooltipsToRender.add(new TooltipData(text, x, y));
    }


    private static void renderTooltips() {
        GlStateManager.pushMatrix();
        GlStateManager.enableLighting();
        for (TooltipData tooltipData : tooltipsToRender) {
            getOverlayedGui().drawHoveringText(tooltipData.text, tooltipData.x, tooltipData.y);
        }
        GlStateManager.disableLighting();
        tooltipsToRender.clear();
        GlStateManager.popMatrix();

    }

    public static boolean mouseClick(int x, int y, int button) {
        if (aeiGui.visible) {
            for (Control control : aeiGui.controls) {
                if (control.isHighlighted() && control.isEnabled() && control.onClick != null) {
                    if (focusedControl != null)
                        focusedControl.setFocused(false);
                    if (control instanceof IFocusable) {
                        focusedControl = (IFocusable) control;
                        ((IFocusable) control).setFocused(true);
                    }
                    return control.onClick.apply(button);
                }
            }
            if (focusedControl != null) {
                focusedControl.setFocused(false);
                focusedControl = null;
            }
        }
        if (overlayedGUI instanceof RecipeGui){
            List<Control> controls = ((RecipeGui)overlayedGUI).controls;
            Optional<Control> ctrl= controls.stream().filter(Control::isHighlighted).filter(Control::isEnabled).findFirst();
            if (ctrl.isPresent()){
                return ctrl.get().onClick.apply(button);
            }
        }
        return false;
    }

    public static boolean keyDown(int typedChar, int keyCode, int unknown) {
        boolean handled = false;
        if (focusedControl != null && focusedControl instanceof Control) {
            Control control = (Control) focusedControl;
            if (control.onKeyDown != null) {
                handled = control.onKeyDown.accept(typedChar, keyCode, unknown);
            }
            if (control.charPressed != null)
                if (typedChar == 256) {
                    ((IFocusable) control).setFocused(false);
                    focusedControl = null;
                }
            handled = true;
        }
        if (!handled){
            return KeyBindManager.processGuiKeybinds(typedChar);
        }
        return handled;
    }

    public static boolean charInput(long num, int keyCode, int unknown) {
        if (focusedControl != null && focusedControl instanceof Control) {
            Control control = (Control) focusedControl;
            if (control.charPressed != null) {
                int numChars = Character.charCount(keyCode);
                if (num == numChars)
                    control.charPressed.accept((char) keyCode, unknown);
                else {
                    char[] chars = Character.toChars(keyCode);
                    for (int x = 0; x < chars.length; x++) {
                        control.charPressed.accept(chars[x], unknown);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean mouseScrolled(double direction) {
        if (!aeiGui.visible)
                return false;
        if (direction > 0 && aeiGui.buttonLeft.isEnabled())
            aeiGui.buttonLeft.onClick.apply(0);
        else if(direction < 0 && aeiGui.buttonRight.isEnabled())
            aeiGui.buttonRight.onClick.apply(0);
        return true;
    }

    private static class TooltipData {

        private final List<String> text;
        private final int x;
        private final int y;

        public TooltipData(List<String> text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }
    }

    public static void updateSearch(){
        aeiGui.updateView();
    }
    public static void tick(){
        if (aeiGui !=null && Minecraft.getInstance().currentScreen== overlayedGUI)
            aeiGui.tick();
    }

    public static void recipeKeybind(){
        if (!(Minecraft.getInstance().currentScreen instanceof GuiContainer))
            return;
        Control control = aeiGui.getLastHovered();
        if (control != null && control.isHighlighted() && control instanceof AEISlot) {
            AEISlot slot = (AEISlot) control;
            AEIRecipeManager.instance().displayRecipesFor(slot.getStack());
            return;
        }
        if (((IMixinGuiContainer)overlayedGUI).getHoveredSlot() != null) {
            ItemStack stack = ((IMixinGuiContainer)overlayedGUI).getHoveredSlot().getStack();
            AEIRecipeManager.instance().displayRecipesFor(stack);
        }

    }
    public static void useKeybind(){
        if (!(Minecraft.getInstance().currentScreen instanceof GuiContainer))
            return;
        Control control = aeiGui.getLastHovered();
        if (control != null && control.isHighlighted() && control instanceof AEISlot) {
            AEISlot slot = (AEISlot) control;
            AEIRecipeManager.instance().displayUsesFor(slot.getStack());
            return;
        }
        if (((IMixinGuiContainer)overlayedGUI).getHoveredSlot() != null){
            ItemStack stack = ((IMixinGuiContainer)overlayedGUI).getHoveredSlot().getStack();
            AEIRecipeManager.instance().displayUsesFor(stack);
        }

    }

    public static void hideKeybind(){
        if (Minecraft.getInstance().currentScreen==overlayedGUI && aeiGui!=null){
            aeiGui.visible=!aeiGui.visible;
        }
    }
}
