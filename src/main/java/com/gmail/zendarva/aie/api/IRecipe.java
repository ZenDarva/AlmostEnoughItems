package com.gmail.zendarva.aie.api;

import java.util.List;

/**
 * Created by James on 7/27/2018.
 */
public interface IRecipe<T extends IIngredient> {

    public List<T> getOutput();

    public List<List<T>> getInput();
}
