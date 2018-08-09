package com.gmail.zendarva.aie.domain;

import com.gmail.zendarva.aie.api.IItemStack;
import com.gmail.zendarva.aie.gui.AEIRenderHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack stack = item.getDefaultInstance();
        if (tag != null)
            stack.setTagCompound(tag.copy());
        return stack;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return (this.item == stack.getItem() &&
                this.tag.equals(stack.getTagCompound()) &&
                this.metadata== stack.getItemDamage());
    }

    @Override
    public void draw(int x, int y) {
        GuiContainer gui = AEIRenderHelper.getOverlayedGui();
        gui.zLevel = 200.0F;
        gui.itemRender.zLevel = 200.0F;
        gui.itemRender.renderItemAndEffectIntoGUI(getItemStack(),x,y);
        gui.itemRender.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, getItemStack(), x, y - (gui.draggedStack.isEmpty() ? 0 : 8), "");
        gui.zLevel = 0.0F;
        gui.itemRender.zLevel = 0.0F;
    }

    @Override
    public List<String> getTooltip() {
        Minecraft mc = Minecraft.getMinecraft();
        List unlocalizedTooltip = getItemStack().getTooltip(mc.player, mc.gameSettings.advancedItemTooltips? ITooltipFlag.TooltipFlags.ADVANCED: ITooltipFlag.TooltipFlags.NORMAL);
        ArrayList toolTip = Lists.newArrayList();
        Iterator var4 = unlocalizedTooltip.iterator();

        while(var4.hasNext()) {
            ITextComponent unlocalizedTip = (ITextComponent)var4.next();
            toolTip.add(unlocalizedTip.createCopy().getFormattedText());
        }

        return toolTip;
    }

    @Override
    public String getMod() {
        ResourceLocation location = Item.REGISTRY.getNameForObject(item);
        return location.getNamespace();
    }

    @Override
    public String getName() {
        return item.getName().getString();
    }


}
