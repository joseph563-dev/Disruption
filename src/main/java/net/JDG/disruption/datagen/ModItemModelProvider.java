package net.JDG.disruption.datagen;

import net.JDG.disruption.Disruption;
import net.JDG.disruption.item.ModItems;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider{
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Disruption.MOD_ID, existingFileHelper);
    }

    @Override
            protected void  registerModels() {
        withExistingParent(ModItems.FAKER_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        }

    }

