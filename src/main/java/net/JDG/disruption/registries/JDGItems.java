package net.JDG.disruption.registries;


import net.JDG.disruption.Disruption;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class JDGItems {

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(Registries.ITEM, Disruption.MOD_ID);

    public static  final DeferredHolder<Item, Item> FAKER_SPAWN_EGG = REGISTRY.register("faker_egg",
            ()-> new DeferredSpawnEggItem(JDGEntities.FAKER, 000000, 000000,
                    new Item.Properties()));

}
