package com.gmail.zendarva.aie.domain;

import com.gmail.zendarva.aie.api.IItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.LanguageMap;

/**
 * Created by James on 7/27/2018.
 */
public class AEIItemStack implements IItemStack {

    private Item item;
    private int metadata;
    private NBTTagCompound tag;

    public AEIItemStack(ItemStack stack){
        item = stack.getItem();
        metadata= stack.getItemDamage();
        tag = stack.getTagCompound();

        //System.out.println(LanguageMap.getInstance().translateKey(stack.getTranslationKey()));
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack stack = item.getDefaultInstance();
        if (tag != null)
            stack.setTagCompound(tag.copy());
        return stack;
    }
}
