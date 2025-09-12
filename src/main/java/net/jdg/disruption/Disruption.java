package net.jdg.disruption;

import net.jdg.disruption.event_engine.EventEngine;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod(Disruption.MOD_ID)
public class Disruption {
    public static final String MOD_ID = "disruption";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation moonTexture = ResourceLocation.withDefaultNamespace("textures/environment/moon_phases.png");
    public static ResourceLocation sunTexture = ResourceLocation.withDefaultNamespace("textures/environment/sun.png");
    public static float moonSize = 20f;
    public static float sunSize = 30f;
    public static EventEngine eventEngine;

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
        eventEngine = new EventEngine(bus);

        JDGEntities.register(bus);
        JDGItems.register(bus);
        JDGCreativeTabs.register(bus);
        bus.addListener(JDGResourcePackForcer::periodBABY);
        //This stops the peoples from simply turning off our mix-ins
        MixinForcer.checkMixinList("disruption.mixins.json", getMixinNames());

        /*Extra layer of defense for removal of the mixins, completely unavoidable since
        it is built into the main mod class */
        if (!isClassPresent()) {
            throw new RuntimeException("Ya ok buddy nice try. I thought of that too, goodluck by passing this problem");
        }
    }

    public List<String> getMixinNames() {
        var classes = findAllClassesUsingClassLoader("net.jdg.disruption.mixins");
        var list = new ArrayList<String>();
        classes.forEach((clazz) -> list.add(clazz.getSimpleName()));
        return list;
    }

    @SuppressWarnings("rawtypes")
    public Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("rawtypes")
    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("failed to init mixin classes");
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