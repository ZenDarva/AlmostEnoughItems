package com.gmail.zendarva.aie.api;

import com.gmail.zendarva.aie.gui.Drawable;

import java.util.List;

/**
 * Created by James on 7/27/2018.
 */
public interface IIngredient {

    public void draw(int x, int y);

    public List<String> getTooltip();
}
