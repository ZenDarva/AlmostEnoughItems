package com.gmail.zendarva.aie.api;

import com.gmail.zendarva.aie.gui.widget.AEISlot;
import net.minecraft.item.ItemStack;

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
}
