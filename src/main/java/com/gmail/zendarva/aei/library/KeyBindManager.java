package com.gmail.zendarva.aei.library;

import com.gmail.zendarva.aei.listenerdefinitions.IMixinKeyBinding;
import com.gmail.zendarva.aei.listenerdefinitions.PreLoadOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.settings.GameOptions;
import net.minecraft.client.settings.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

/**
 * Created by James on 8/7/2018.
 */
public class KeyBindManager implements PreLoadOptions {

    private static boolean optionsLoaded =false;
    private static List<KeyBinding> bindingsToAdd = new ArrayList<>();
    private static Map<KeyBinding, Sink> bindingFunctions = new HashMap<>();
    public static KeyBinding createKeybinding(String bindingName, int key, String categoryName, Sink function){
        KeyBinding newBinding;
        MinecraftClient mc = MinecraftClient.getInstance();
        newBinding = new KeyBinding(bindingName, key, categoryName);
        if (optionsLoaded) {
            mc.options.keysAll = ArrayUtils.add(mc.options.keysAll, newBinding);
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
    public void onloadOptions(GameOptions gameSettings) {
        for (KeyBinding Bindings: bindingsToAdd)gameSettings.keysAll = ArrayUtils.add(gameSettings.keysAll, Bindings);
        optionsLoaded = true;
    }

    @Override
    public void processKeybinds() {
        bindingFunctions.keySet().stream().filter(KeyBinding::isPressed).forEach(f->bindingFunctions.get(f).Sink());
    }

    public static boolean processGuiKeybinds(int typedChar){
        Optional<KeyBinding> binding= bindingFunctions.keySet().stream().filter(f->f.getDefaultKeyCode().getKeyCode() == typedChar).findFirst();
        if (binding.isPresent()){
            bindingFunctions.get(binding.get()).Sink();
            return true;
        }
        return false;
    }
}
