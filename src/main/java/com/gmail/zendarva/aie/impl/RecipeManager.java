package com.gmail.zendarva.aie.impl;

import com.gmail.zendarva.aie.api.ICategory;
import com.gmail.zendarva.aie.api.IDisplayAdapter;
import com.gmail.zendarva.aie.api.IRecipe;
import com.gmail.zendarva.aie.api.IRecipeManager;

import javax.naming.NameAlreadyBoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by James on 8/7/2018.
 */
public class RecipeManager implements IRecipeManager {
    private Map<String,List<IRecipe>> recipeList;
    private Map<String,IDisplayAdapter> displayAdapters;

    private static RecipeManager myInstance;

    private RecipeManager(){
        recipeList = new HashMap<>();
        displayAdapters = new HashMap<>();
    }

    public static RecipeManager instance(){
        if (myInstance == null){
            myInstance= new RecipeManager();
        }
        return myInstance;
    }

    @Override
    public void addRecipe(String id, IRecipe recipe) {
        if (recipeList.containsKey(id)){
            recipeList.get(id).add(recipe);
        }
        else{
            List<IRecipe> recipes = new LinkedList<>();
            recipeList.put(id,recipes);
            recipes.add(recipe);
        }


    }

    @Override
    public void addRecipe(String id, List<IRecipe> recipes) {
        if (recipeList.containsKey(id)){
            recipeList.get(id).addAll(recipes);
        }
        else{
            List<IRecipe> newRecipeList = new LinkedList<>();
            recipeList.put(id,newRecipeList);
            newRecipeList.addAll(recipes);
        }
    }

    @Override
    public void addDisplayAdapter(String id, IDisplayAdapter adapter) throws NameAlreadyBoundException {
        if (displayAdapters.containsKey(id)){
            throw new NameAlreadyBoundException(id+" already has a display adapter bound to it.");
        }
        displayAdapters.put(id,adapter);
    }

    @Override
    public void addCategory(String id, ICategory category) {

    }
}
