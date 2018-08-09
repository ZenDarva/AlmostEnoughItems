package com.gmail.zendarva.aie;

import com.gmail.zendarva.aie.api.IAEIPlugin;
import com.gmail.zendarva.aie.api.IIngredient;
import com.gmail.zendarva.aie.domain.AEIItemStack;
import com.gmail.zendarva.aie.gui.AEIRenderHelper;
import com.gmail.zendarva.aie.impl.AEIRecipeManager;
import com.gmail.zendarva.aie.library.KeyBindManager;
import com.gmail.zendarva.aie.listenerdefinitions.DoneLoading;
import com.gmail.zendarva.aie.listenerdefinitions.RecipeLoadListener;
import com.gmail.zendarva.aie.network.CheatPacket;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.util.NonNullList;
import org.dimdev.rift.listener.PacketAdder;
import org.dimdev.riftloader.RiftLoader;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 7/27/2018.
 */
public class Core implements DoneLoading, PacketAdder, RecipeLoadListener {
    private List<IAEIPlugin> plugins;
    public static List<IIngredient> ingredientList;
    public static KeyBinding recipeKeybind;
    public static KeyBinding hideKeybind;
    @Override
    public void onDoneLoading() {
        plugins = new ArrayList<>();
        ingredientList = new ArrayList<>();
        findPlugins();
        buildItemList();
        setupKeybinds();
    }

    private void setupKeybinds() {
        recipeKeybind = KeyBindManager.createKeybinding("key.aei.recipe", KeyEvent.VK_R,"key.aei.category", AEIRenderHelper::recipeKeybind);
        hideKeybind = KeyBindManager.createKeybinding("key.aei.hide", KeyEvent.VK_O ,"key.aei.category", AEIRenderHelper::hideKeybind);

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
                items.forEach(f->ingredientList.add(new AEIItemStack(f)));
            }
            catch(NullPointerException e) {
                if (item == Items.ENCHANTED_BOOK){
                    item.getSubItems(ItemGroup.TOOLS, items);
                    items.forEach(f->ingredientList.add(new AEIItemStack(f)));
                }
            }
    }

    @Override
    public void registerHandshakingPackets(PacketRegistrationReceiver receiver) {
    }

    @Override
    public void registerPlayPackets(PacketRegistrationReceiver receiver) {
        receiver.registerPacket(EnumPacketDirection.SERVERBOUND,CheatPacket.class);
    }

    @Override
    public void registerStatusPackets(PacketRegistrationReceiver receiver) {

    }

    @Override
    public void registerLoginPackets(PacketRegistrationReceiver receiver) {

    }

    @Override
    public void recipesLoaded(net.minecraft.item.crafting.RecipeManager recipeManager) {
        AEIRecipeManager.instance().recipeManager = recipeManager;
        RiftLoader.instance.getListeners(IAEIPlugin.class).forEach(IAEIPlugin::register);
    }
}
