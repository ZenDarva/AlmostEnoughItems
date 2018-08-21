package com.gmail.zendarva.aei;

import com.gmail.zendarva.aei.api.IAEIPlugin;
import com.gmail.zendarva.aei.gui.AEIRenderHelper;
import com.gmail.zendarva.aei.impl.AEIRecipeManager;
import com.gmail.zendarva.aei.library.KeyBindManager;
import com.gmail.zendarva.aei.listenerdefinitions.DoneLoading;
import com.gmail.zendarva.aei.listenerdefinitions.RecipeLoadListener;
import com.gmail.zendarva.aei.network.CheatPacket;
import com.gmail.zendarva.aei.network.DeletePacket;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.util.NonNullList;
import org.dimdev.rift.listener.PacketAdder;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 7/27/2018.
 */
public class Core implements DoneLoading, PacketAdder, RecipeLoadListener {
    private List<IAEIPlugin> plugins;
    public static List<ItemStack> stackList;
    public static KeyBinding recipeKeybind;
    public static KeyBinding hideKeybind;
    public static KeyBinding useKeybind;
    @Override
    public void onDoneLoading() {
        plugins = new ArrayList<>();
        stackList = new ArrayList<>();
        findPlugins();
        buildItemList();
        setupKeybinds();
    }

    private void setupKeybinds() {
        recipeKeybind = KeyBindManager.createKeybinding("key.aei.recipe", KeyEvent.VK_R,"key.aei.category", AEIRenderHelper::recipeKeybind);
        hideKeybind = KeyBindManager.createKeybinding("key.aei.hide", KeyEvent.VK_O ,"key.aei.category", AEIRenderHelper::hideKeybind);
        useKeybind = KeyBindManager.createKeybinding("key.aei.use", KeyEvent.VK_U,"key.aei.category", AEIRenderHelper::useKeybind);
    }

    private void buildItemList() {
        Item.REGISTRY.forEach(this::processItem);
    }

    private void findPlugins() {


    }

    private void processItem(Item item){
            NonNullList<ItemStack> items = NonNullList.create();
            try {
                item.getSubItems(item.getCreativeTab(), items);
                items.forEach(stackList::add);
            }
            catch(NullPointerException e) {
                if (item == Items.ENCHANTED_BOOK){
                    item.getSubItems(ItemGroup.TOOLS, items);
                    items.forEach(stackList::add);
                }
            }
    }

    @Override
    public void registerHandshakingPackets(PacketRegistrationReceiver receiver) {
    }

    @Override
    public void registerPlayPackets(PacketRegistrationReceiver receiver) {
        receiver.registerPacket(EnumPacketDirection.SERVERBOUND,CheatPacket.class);
        receiver.registerPacket(EnumPacketDirection.SERVERBOUND, DeletePacket.class);
    }

    @Override
    public void registerStatusPackets(PacketRegistrationReceiver receiver) {

    }

    @Override
    public void registerLoginPackets(PacketRegistrationReceiver receiver) {

    }

    @Override
    public void recipesLoaded(net.minecraft.item.crafting.RecipeManager recipeManager) {
        AEIRecipeManager.instance().RecipesLoaded(recipeManager);
    }
}
