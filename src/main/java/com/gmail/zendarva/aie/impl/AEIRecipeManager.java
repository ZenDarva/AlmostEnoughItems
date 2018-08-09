package com.gmail.zendarva.aie.impl;

import com.gmail.zendarva.aie.api.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by James on 8/7/2018.
 */
public class AEIRecipeManager implements IRecipeManager {
    private Map<String,List<IRecipe>> recipeList;
    private List<IDisplayCategory> displayAdapters;
    public static RecipeManager recipeManager;

    private static AEIRecipeManager myInstance;

    private AEIRecipeManager(){
        recipeList = new HashMap<>();
        displayAdapters = new LinkedList<>();
    }

    public static AEIRecipeManager instance(){
        if (myInstance == null){
            myInstance= new AEIRecipeManager();
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
    public void addRecipe(String id, List<? extends IRecipe> recipes) {
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
    public void addDisplayAdapter(IDisplayCategory adapter) {
        displayAdapters.add(adapter);
    }

    @Override
    public List<IRecipe> getRecipesFor(ItemStack stack) {
        List recipes = new LinkedList();
        for (List<IRecipe> value : recipeList.values()) {
            for (IRecipe iRecipe : value) {
                for (Object o : iRecipe.getOutput()) {
                    if (o instanceof ItemStack)
                        if(ItemStack.areItemStacksEqual(stack, (ItemStack) o))
                            recipes.add(iRecipe);
                }
            }
        }
        return recipes;
    }


    public List<IDisplayCategory> getAdatapersForOutput(IIngredient stack){
        return null;
    }

    public List<IDisplayCategory> getAdaptersForOutput (Item item){
        return null;
    }
}
