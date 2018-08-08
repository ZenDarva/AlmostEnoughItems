package com.gmail.zendarva.aie.api;

import java.util.List;

/**
 * Created by James on 7/27/2018.
 */
public interface IRecipe {

    public List<IIngredient> getOutput();

    public List<List<IIngredient>> getInput();
}
