package com.gmail.zendarva.aei.mixins;

import com.gmail.zendarva.aei.listenerdefinitions.IMixinKeyBinding;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(KeyBinding.class)
public class MixinKeyBinding implements IMixinKeyBinding {
    @Shadow
    private static Map<String, Integer> CATEGORY_ORDER;
    @Override
    public boolean addCategoryOrder(String keyCategory,Integer Id){
        if(CATEGORY_ORDER.get(keyCategory) == null){
            CATEGORY_ORDER.put(keyCategory,Id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean addCategoryOrder(String keyCategory) {
        return addCategoryOrder(keyCategory,CATEGORY_ORDER.size()+1);
    }

    @Override
    public boolean hasCategoryName(String keyCategory){
        return CATEGORY_ORDER.get(keyCategory) != null;
    }
}
