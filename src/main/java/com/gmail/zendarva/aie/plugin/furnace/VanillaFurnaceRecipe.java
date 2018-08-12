package com.gmail.zendarva.aie.plugin.furnace;

import com.gmail.zendarva.aie.api.IRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;

import java.util.List;

public class VanillaFurnaceRecipe implements IRecipe<FurnaceRecipe> {
    @Override
    public String getId() {
        return "furnace";
    }

    @Override
    public List<FurnaceRecipe> getOutput() {
        return null;
    }

    @Override
    public List<List<FurnaceRecipe>> getInput() {
        return null;
    }
}
