package com.gmail.zendarva.aei.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

/**
 * Created by James on 7/27/2018.
 */
public class InitListener {
    private static final Logger LOGGER = LogManager.getLogger();

    // TODO - ModInitializer for 1.14 (Possible Removal bc of Load Methods)
    public void onInitialization() {
        LOGGER.info("Adding AEI Mixins");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.aei.json");

    }
}
