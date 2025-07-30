package net.JDG.disruption.item;


import net.JDG.disruption.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;

import static net.JDG.disruption.Disruption.ITEMS;

public class ModItems {
    public static  final DeferredItem<Item> FAKER_SPAWN_EGG = ITEMS.register("faker_spawn_egg",
            ()-> new DeferredSpawnEggItem(ModEntities.FAKER, 000000, 000000,
                    new Item.Properties()));

}
