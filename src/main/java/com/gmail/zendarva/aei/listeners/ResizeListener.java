package com.gmail.zendarva.aei.listeners;

import com.gmail.zendarva.aei.gui.AEIRenderHelper;
import com.gmail.zendarva.aei.listenerdefinitions.MinecraftResize;

/**
 * Created by James on 7/28/2018.
 */
public class ResizeListener implements MinecraftResize {
    @Override
    public void resize() {
        AEIRenderHelper.resize();
    }
}
