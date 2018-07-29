package com.gmail.zendarva.aie;

import com.gmail.zendarva.aie.api.IAEIPlugin;
import com.gmail.zendarva.aie.api.IIngredient;
import com.gmail.zendarva.aie.domain.AEIItemStack;
import com.gmail.zendarva.aie.listenerdefinitions.DoneLoading;
import com.gmail.zendarva.aie.network.CheatPacket;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.util.NonNullList;
import org.dimdev.rift.listener.PacketAdder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 7/27/2018.
 */
public class Core implements DoneLoading, PacketAdder {
    private List<IAEIPlugin> plugins;
    public static List<IIngredient> ingredientList;
    @Override
    public void onDoneLoading() {
        plugins = new ArrayList<>();
        ingredientList = new ArrayList<>();
        findPlugins();
        buildItemList();
    }

    private void buildItemList() {
        Item.REGISTRY.forEach(this::processItem);
    }

    private void findPlugins() {
        ScanResult scanner = new FastClasspathScanner().enableExternalClasses().scan();
        List<String> classes = scanner.getNamesOfClassesWithAnnotation("com.gmail.zendarva.aie.api.AEIPlugin");
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
}
