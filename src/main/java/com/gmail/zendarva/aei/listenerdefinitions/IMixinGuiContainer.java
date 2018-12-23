package com.gmail.zendarva.aei.listenerdefinitions;

import net.minecraft.container.Slot;
import net.minecraft.item.ItemStack;

public interface IMixinGuiContainer {
    public ItemStack getDraggedStack();
    public int getGuiLeft();
    public int getXSize();
    public Slot getHoveredSlot();
}
