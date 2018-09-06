package com.gmail.zendarva.aei.mixins;

import com.gmail.zendarva.aei.listenerdefinitions.RecipeLoadListener;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.item.crafting.RecipeManager;
import org.dimdev.riftloader.RiftLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinRecipeManager {

    @Shadow
    RecipeManager recipeManager;

    @Inject(method = "func_199525_a", at = @At("RETURN"))
    private void onBootstrapRegister(CallbackInfo ci) {
        for (RecipeLoadListener listener : RiftLoader.instance.getListeners(RecipeLoadListener.class)) {
            listener.recipesLoaded(recipeManager);
        }
    }
}
