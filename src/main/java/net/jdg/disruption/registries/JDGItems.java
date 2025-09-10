package net.jdg.disruption.registries;


import net.jdg.disruption.Disruption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class JDGItems {
    /**
     * This class is where you register your items so they can be used.
     */
    public static final DeferredRegister<Item> JDG_ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, Disruption.MOD_ID);

    public static  final DeferredHolder<Item, Item> FAKER_SPAWN_EGG = JDG_ITEMS.register("faker_egg",
            ()-> new DeferredSpawnEggItem(JDGEntities.FAKER, 000000, 000000,
                    new Item.Properties())
    );

    public static  final DeferredHolder<Item, Item> MAGNUS_SPAWN_EGG = JDG_ITEMS.register("magnus_egg",
            ()-> new DeferredSpawnEggItem(JDGEntities.MAGNUS, 100000, 000000,
                    new Item.Properties())
    );

    public static  final DeferredHolder<Item, Item> ZACK_SPAWN_EGG = JDG_ITEMS.register("zack_egg",
            ()-> new DeferredSpawnEggItem(JDGEntities.ZACK, 200000, 000000,
                    new Item.Properties())
    );

    public static void register(IEventBus eventBus) {
        JDG_ITEMS.register(eventBus);
    }

}

