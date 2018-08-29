package com.gmail.zendarva.aei.mixins;

import com.gmail.zendarva.aei.listenerdefinitions.CharInput;
import net.minecraft.client.KeyboardListener;
import org.dimdev.riftloader.RiftLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by James on 8/4/2018.
 */
@Mixin(KeyboardListener.class)
public class MixinKeyboardListener {
    @Inject(method = "onCharEvent", at = @At("RETURN"), cancellable = true)
    private void onCharEvent(long p_onCharEvent_1_, int p_onCharEvent_3_, int p_onCharEvent_4_, CallbackInfo ci) {
        boolean handled = false;
        for (CharInput listener : RiftLoader.instance.getListeners(CharInput.class)) {
            if (listener.charInput(p_onCharEvent_1_,p_onCharEvent_3_, p_onCharEvent_4_)) {
                handled= true;
            }
        }
        if (handled)
            ci.cancel();
    }

//    @Inject(method = "onKeyPressed", at = @At("HEAD"), cancellable = true)
//    private void onKeyPressed(long p_onKeyPressed_1_, int p_onKeyPressed_3_, int p_onKeyPressed_4_, int p_onKeyPressed_5_, int p_onKeyPressed_6_, CallbackInfo ci) {
//        boolean handled = false;
//        for (GuiKeyDown listener : RiftLoader.instance.getListeners(GuiKeyDown.class)) {
//            if (listener.keyDown(p_onKeyPressed_3_,p_onKeyPressed_4_,p_onKeyPressed_6_))
//                handled = true;
//        }
//        if (handled) {
//            System.out.println("Canceling keypress");
//            ci.cancel();
//        }
//    }
}
