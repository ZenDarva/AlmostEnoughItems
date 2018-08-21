package com.gmail.zendarva.aei.mixins;

import com.gmail.zendarva.aei.listenerdefinitions.RecipeLoadListener;
import net.minecraft.item.crafting.RecipeManager;
import org.dimdev.riftloader.RiftLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeManager.class)
public class MixinRecipeManager {

    @Inject(method = "reload", at = @At("RETURN"))
    private void onBootstrapRegister(CallbackInfo ci) {
        for (RecipeLoadListener listener : RiftLoader.instance.getListeners(RecipeLoadListener.class)) {
            listener.recipesLoaded((RecipeManager)(Object)this);
        }
    }
}
