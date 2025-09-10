package net.jdg.disruption.forcers;

import net.jdg.disruption.Disruption;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class JDGResourcePackForcer {

    /**
     * This class makes you resourcepack JD, I moved it out from the main
     * resources. Basically it is a forced resourcepack that can't be removed
     * nor overridden :3
     * Also anytime you want to edit them simply go into resources/assets/packs/assets/beta_resources
     */

    public static final PackSelectionConfig BETTER_SELECTION = new PackSelectionConfig(true, Pack.Position.TOP,true );

    public static void periodBABY(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(Disruption.MOD_ID).getFile().findResource("packs/beata_resources");
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable(":thumbs_up:"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                                    new PackLocationInfo("builtin/beta_resources", Component.translatable("."), PackSource.BUILT_IN, Optional.empty()),
                                    new PathPackResources.PathResourcesSupplier(resourcePath),
                                    new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                                    BETTER_SELECTION
                            )
                    )
            );
            System.out.println("Heh, WAZAAAAAAAA");
        }
    }
}