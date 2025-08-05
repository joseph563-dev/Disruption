package net.JDG.disruption;

import net.JDG.disruption.registries.JDGEntities;
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

    public Disruption(IEventBus modEventBus, ModContainer modContainer) {

        NeoForge.EVENT_BUS.register(this);


        JDGEntities.register(modEventBus);

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Mrow, we on a server");
    }
}