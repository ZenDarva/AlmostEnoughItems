package com.gmail.zendarva.aei.library;

import com.gmail.zendarva.aei.listenerdefinitions.IMixinKeyBinding;
import com.gmail.zendarva.aei.listenerdefinitions.PreLoadOptions;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;
import org.dimdev.rift.listener.client.KeybindHandler;

import java.util.*;

/**
 * Created by James on 8/7/2018.
 */
public class KeyBindManager implements PreLoadOptions, KeybindHandler {

    private static boolean optionsLoaded =false;
    private static List<KeyBinding> bindingsToAdd = new ArrayList<>();
    private static Map<KeyBinding, Sink> bindingFunctions = new HashMap<>();
    public static KeyBinding createKeybinding(String bindingName, int key, String categoryName, Sink function){
        KeyBinding newBinding;
        Minecraft mc = Minecraft.getInstance();
        newBinding = new KeyBinding(bindingName, key, categoryName);
        if (optionsLoaded) {
            mc.gameSettings.keyBindings = ArrayUtils.add(mc.gameSettings.keyBindings, newBinding);
        } else {
            bindingsToAdd.add(newBinding);
        }
        bindingFunctions.put(newBinding,function);
        addCategoryIfMissing(newBinding,categoryName);
        return newBinding;
    }
    private static void addCategoryIfMissing(KeyBinding newBinding ,String categoryName) {
        if(!((IMixinKeyBinding)newBinding).hasCategoryName(categoryName)){
            ((IMixinKeyBinding)newBinding).addCategoryOrder(categoryName);
        }
    }

    @Override
    public void onloadOptions(GameSettings gameSettings) {
        for (KeyBinding Bindings: bindingsToAdd)gameSettings.keyBindings = ArrayUtils.add(gameSettings.keyBindings, Bindings);
        optionsLoaded = true;
    }

    @Override
    public void processKeybinds() {
        bindingFunctions.keySet().stream().filter(KeyBinding::isPressed).forEach(f->bindingFunctions.get(f).Sink());
    }

    public static boolean processGuiKeybinds(int typedChar){
        Optional<KeyBinding> binding= bindingFunctions.keySet().stream().filter(f->f.getDefault().getKeyCode() == typedChar).findFirst();
        if (binding.isPresent()){
            bindingFunctions.get(binding.get()).Sink();
            return true;
        }
        return false;
    }
}
