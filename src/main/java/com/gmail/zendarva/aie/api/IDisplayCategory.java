package com.gmail.zendarva.aie.api;

/**
 * Created by James on 8/7/2018.
 */
public interface IDisplayCategory<T extends IRecipe> {
    public String getId();

    public void setRecipe( T recipe);

    public void setupDisplay();

    public boolean canDisplay(IRecipe recipe);
}
