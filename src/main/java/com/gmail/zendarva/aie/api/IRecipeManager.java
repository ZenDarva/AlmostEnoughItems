package com.gmail.zendarva.aie.api;

import javax.naming.NameAlreadyBoundException;
import java.util.List;

/**
 * Created by James on 8/5/2018.
 */
public interface IRecipeManager {

    public void addRecipe(String id, IRecipe recipe);
    public void addRecipe(String id, List<IRecipe> recipes);
    public void addDisplayAdapter(String id, IDisplayAdapter adapter) throws NameAlreadyBoundException;
    public void addCategory(String id, ICategory category);
}
