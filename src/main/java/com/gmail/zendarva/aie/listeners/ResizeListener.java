package com.gmail.zendarva.aie.listeners;

import com.gmail.zendarva.aie.gui.AEIRenderHelper;
import com.gmail.zendarva.aie.listenerdefinitions.MinecraftResize;

/**
 * Created by James on 7/28/2018.
 */
public class ResizeListener implements MinecraftResize {
    @Override
    public void resize() {
        AEIRenderHelper.resize();
    }
}
