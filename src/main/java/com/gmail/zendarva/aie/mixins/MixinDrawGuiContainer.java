package com.gmail.zendarva.aie.mixins;

import com.gmail.zendarva.aie.listenerdefinitions.DoneLoading;
import com.gmail.zendarva.aie.listenerdefinitions.DrawContainer;
import com.gmail.zendarva.aie.listenerdefinitions.GuiCickListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.dimdev.riftloader.RiftLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created by James on 7/27/2018.
 */
@Mixin(GuiContainer.class)
public class MixinDrawGuiContainer {
    @Inject(method = "drawScreen", at = @At("RETURN"))
    private void onDrawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_, CallbackInfo ci) {
        for (DrawContainer listener : RiftLoader.instance.getListeners(DrawContainer.class)) {
            listener.draw(p_drawScreen_1_,p_drawScreen_2_,p_drawScreen_3_, (GuiContainer) Minecraft.getMinecraft().currentScreen);
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void onMouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_, CallbackInfoReturnable<Boolean> ci) {
        boolean handled = false;
        for (GuiCickListener listener : RiftLoader.instance.getListeners(GuiCickListener.class)) {
            if (listener.onClick((int)p_mouseClicked_1_,(int)p_mouseClicked_3_, p_mouseClicked_5_)) {
                ci.setReturnValue(true);
                handled= true;
            }
        }
        if (handled)
            ci.cancel();

    }
}
