package net.JDG.disruption;

import net.JDG.disruption.registries.JDGCreativeTabs;
import net.JDG.disruption.registries.JDGEntities;
import net.JDG.disruption.registries.JDGItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Disruption.MOD_ID)
public class Disruption {
    public static final String MOD_ID = "disruption";
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * This is the mods eventbus, basically a different way of subscribing an event
     * Mostly this will be used for debug logging and registering the registers.
     */

    public Disruption(IEventBus bus, ModContainer modContainer) {

        if (IS_DEV) {
            System.out.println("Purely a test. You, sir, are in dev mode");
        }

        NeoForge.EVENT_BUS.register(this);

        JDGEntities.register(bus);
        JDGItems.register(bus);
        JDGCreativeTabs.register(bus);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Mrow, we on a server");
    }

    /**
     * This funny little guy is used for Dev only features
     * One of those being in {@link JDGCreativeTabs}
     * To access these features you need to add DEV=true to your EVs
     * If you don't know how ask me
     */

    public static final boolean IS_DEV = "true".equalsIgnoreCase(System.getenv("DEV"));
}