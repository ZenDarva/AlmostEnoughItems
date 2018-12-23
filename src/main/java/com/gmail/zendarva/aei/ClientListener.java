package com.gmail.zendarva.aei;

import com.gmail.zendarva.aei.api.IAEIPlugin;
import com.gmail.zendarva.aei.gui.AEIRenderHelper;
import com.gmail.zendarva.aei.impl.AEIRecipeManager;
import com.gmail.zendarva.aei.library.KeyBindManager;
import com.gmail.zendarva.aei.listenerdefinitions.DoneLoading;
import com.gmail.zendarva.aei.listenerdefinitions.RecipeLoadListener;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ClientListener implements DoneLoading, RecipeLoadListener {
    public static KeyBinding recipeKeybind;
    public static KeyBinding hideKeybind;
    public static KeyBinding useKeybind;

    private List<IAEIPlugin> plugins;
    public static List<ItemStack> stackList;

    @Override
    public void onDoneLoading() {

        plugins = new ArrayList<>();
        stackList = new ArrayList<>();

        recipeKeybind = KeyBindManager.createKeybinding("key.aei.recipe", KeyEvent.VK_R,"key.aei.category", AEIRenderHelper::recipeKeybind);
        hideKeybind = KeyBindManager.createKeybinding("key.aei.hide", KeyEvent.VK_O ,"key.aei.category", AEIRenderHelper::hideKeybind);
        useKeybind = KeyBindManager.createKeybinding("key.aei.use", KeyEvent.VK_U,"key.aei.category", AEIRenderHelper::useKeybind);

        buildItemList();
    }

    private void buildItemList() {
        if(!Registry.ITEM.isEmpty()) {
            Registry.ITEM.forEach(item->processItem(item));
        }

    }

    private void processItem(Item item){
        DefaultedList<ItemStack> items = DefaultedList.create();
        try {
            item.fillItemGroup(item.getItemGroup(),items);
            items.forEach(stackList::add);
        }
        catch(NullPointerException e) {
            if (item == Items.ENCHANTED_BOOK){
                item.fillItemGroup(ItemGroup.TOOLS, items);
                items.forEach(stackList::add);
            }
        }
    }

    @Override
    public void recipesLoaded(RecipeManager recipeManager) {
        AEIRecipeManager.instance().RecipesLoaded(recipeManager);
    }}
