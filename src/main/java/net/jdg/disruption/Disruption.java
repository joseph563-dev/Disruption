package net.jdg.disruption;

import com.google.gson.GsonBuilder;
import net.jdg.disruption.forcers.JDGResourcePackForcer;
import net.jdg.disruption.forcers.MixinForcer;
import net.jdg.disruption.registries.JDGCreativeTabs;
import net.jdg.disruption.registries.JDGEntities;
import net.jdg.disruption.registries.JDGItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;

@Mod(Disruption.MOD_ID)
public class Disruption {
    public static final String MOD_ID = "disruption";
    public static final Logger LOGGER = LogUtils.getLogger();

    //yes :thumbs_up:

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
        bus.addListener(JDGResourcePackForcer::periodBABY);

        //This stops the peoples from simply turning off our mix-ins
        MixinForcer.checkMixinList("disruption.mixins.json", List.of(
                "NarMixin",
                "NarNarMixin",
                "NarNarNarMixin"
        ));

        /*Extra layer of defense for removal of the mixins, completely unavoidable since
        it is built into the main mod class */
        if (!isClassPresent()) {
            throw new RuntimeException("Ya ok buddy nice try. I thought of that too, goodluck by passing this problem");
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Mrow, we on a server");
    }

    public static ResourceLocation createId(String name) {
        return ResourceLocation.fromNamespaceAndPath("disruption", name);
    }

    /**
     * Checks for the {@link MixinForcer} class so we can crash these sneaky buggers
     * They won't even see the RuntimeException error message LOL
     */

    private boolean isClassPresent() {
        try {
            Class.forName("net.jdg.disruption.forcers.MixinForcer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * This funny little guy is used for Dev only features
     * One of those being in {@link JDGCreativeTabs}
     * To access these features you need to add DEV=true to your EVs
     * If you don't know how ask me
     * Also to make something dev only all you need is this little line
     * if (Disruption.IS_DEV) {
     */

    public static final boolean IS_DEV = "true".equalsIgnoreCase(System.getenv("DEV"));
}