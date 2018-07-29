package com.gmail.zendarva.aie.gui.widget;

import com.gmail.zendarva.aie.gui.Drawable;

import java.awt.*;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/**
 * Created by James on 7/29/2018.
 */
public abstract class Control extends Drawable {
    private boolean enabled = true;
    public IntFunction<Boolean> onClick;
    public Control(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Control(Rectangle rect) {
        super(rect);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
