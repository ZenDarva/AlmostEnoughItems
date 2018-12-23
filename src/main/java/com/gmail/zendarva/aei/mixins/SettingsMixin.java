package com.gmail.zendarva.aei.mixins;
import com.gmail.zendarva.aei.listenerdefinitions.PreLoadOptions;
import net.minecraft.client.settings.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(GameOptions.class)
public class SettingsMixin {

    @Inject(method = "loadOptions", at = @At("HEAD"))
    public void onLoadOptions(CallbackInfo ci){
        for (PreLoadOptions listener : RiftLoader.instance.getListeners(PreLoadOptions.class)) {
            listener.onloadOptions((GameOptions) (Object) this);
        }
    }

}
