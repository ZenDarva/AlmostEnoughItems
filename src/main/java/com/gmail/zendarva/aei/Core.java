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
public class Core implements PacketAdder {
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


}
