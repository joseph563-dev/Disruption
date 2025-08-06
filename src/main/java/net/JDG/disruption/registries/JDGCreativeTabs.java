package net.JDG.disruption.registries;

import net.JDG.disruption.Disruption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JDGCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> JDG_CREATIVE_TABS =
            DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Disruption.MOD_ID);

    /**
     * Here is where you can make your own creative tabs, my example here is spawn eggs
     * You can add whatever here, by the way everything in here is a dev-only feature
     * Don't know what that is or how to use it go to {@link Disruption#IS_DEV}
     */

    public static final Supplier<CreativeModeTab> JDG_EGG_TAB = JDG_CREATIVE_TABS.register(
            "tab_egg",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("disruption.tab_egg"))
                    .icon(() -> new ItemStack(Items.OBSIDIAN))
                    .displayItems((parameters, tab) -> {
                        if (Disruption.IS_DEV) {
                            tab.accept(JDGItems.FAKER_SPAWN_EGG.get());
                            tab.accept(JDGItems.MAGNUS_SPAWN_EGG.get());
                            tab.accept(JDGItems.ZACK_SPAWN_EGG.get());
                        }
                    })
                    .withSearchBar()
                    .build()
    );

    public static void register(IEventBus eventBus) {
        JDG_CREATIVE_TABS.register(eventBus);
    }
}
