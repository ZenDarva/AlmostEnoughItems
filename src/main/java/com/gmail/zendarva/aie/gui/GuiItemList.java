package com.gmail.zendarva.aie.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

/**
 * Created by James on 7/28/2018.
 */
public class GuiItemList {

    private final int left;
    private final int top;
    private final GuiContainer overlayedGui;

    public GuiItemList(int left, int top, GuiContainer overlayedGui){

        this.left = left;
        this.top = top;
        this.overlayedGui = overlayedGui;
    }
}
