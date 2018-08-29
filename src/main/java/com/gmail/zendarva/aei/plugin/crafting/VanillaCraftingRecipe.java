package com.gmail.zendarva.aei.plugin.crafting;

import com.gmail.zendarva.aei.api.IRecipe;
import net.minecraft.item.ItemStack;

public abstract class VanillaCraftingRecipe implements IRecipe<ItemStack> {

    public int getWidth(){
        return 2;
    };
    public int getHeight(){
        return 2;
    };
}
