package com.gmail.zendarva.aei.api;

import com.gmail.zendarva.aei.gui.widget.AEISlot;
import com.gmail.zendarva.aei.gui.widget.Control;

import java.util.List;

/**
 * Created by James on 8/7/2018.
 */
public interface IDisplayCategory<T extends IRecipe> {
    public String getId();

    public String getDisplayName();

    public void setRecipe( T recipe);

    public List<AEISlot> setupDisplay();

    public boolean canDisplay(T recipe);

    public void drawExtras();

    public void addWidget(List<Control> controls);
}
