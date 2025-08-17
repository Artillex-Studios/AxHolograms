package com.artillexstudios.axholograms.data;

import com.artillexstudios.axapi.config.YamlConfiguration;
import com.artillexstudios.axapi.config.adapters.MapConfigurationGetter;
import com.artillexstudios.axapi.libs.snakeyaml.DumperOptions;
import com.artillexstudios.axapi.utils.Location;
import com.artillexstudios.axapi.utils.logging.LogUtils;
import com.artillexstudios.axholograms.api.AxHologramsAPI;
import com.artillexstudios.axholograms.api.holograms.data.HologramPageData;
import com.artillexstudios.axholograms.api.holograms.type.HologramType;
import com.artillexstudios.axholograms.api.serializer.LocationSerializer;
import com.artillexstudios.axholograms.config.Config;
import com.artillexstudios.axholograms.hologram.Hologram;
import com.artillexstudios.axholograms.hologram.HologramPage;
import com.artillexstudios.axholograms.utils.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class HologramLoader {

    public static void loadAll() {
        try {
            Files.createDirectories(FileUtils.HOLOGRAMS_DIRECTORY);
        } catch (IOException exception) {
            LogUtils.error("Failed to create holograms directory!", exception);
        }
        Collection<File> files = org.apache.commons.io.FileUtils.listFiles(FileUtils.HOLOGRAMS_DIRECTORY.toFile(), new String[]{"yml", "yaml"}, true);

        if (Config.debug) {
            LogUtils.debug("Loading files: {}", files);
        }
        for (File file : files) {
            YamlConfiguration<?> configuration = YamlConfiguration.of(file.toPath())
                    .withDumperOptions(options -> {
                        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                        options.setPrettyFlow(true);
                    })
                    .build();

            configuration.load();

            String name = FilenameUtils.getBaseName(file.getName());
            Location location = LocationSerializer.INSTANCE.deserialize(configuration.getString("location"));
            Hologram hologram = new Hologram(name, location, true);
            List<MapConfigurationGetter> getters = configuration.getList("pages", object -> {
                if (Config.debug) {
                    LogUtils.debug("Page: {}, class: {}", object, object.getClass());
                }
                if (!(object instanceof Map<?, ?> map)) {
                    return null;
                }

                return new MapConfigurationGetter(map);
            });
            for (MapConfigurationGetter getter : getters) {
                String typeName = getter.getString("type");
                if (typeName == null) {
                    LogUtils.warn("Failed to load hologram named {}, because a page is missing its type!", name);
                    continue;
                }

                HologramType<?> type = AxHologramsAPI.getInstance().getHologramTypeByName(typeName);
                if (type == null) {
                    LogUtils.warn("Failed to load hologram named {}, because the type of a page is invalid! Valid types: {}", name, AxHologramsAPI.getInstance().getTypes().names());
                    continue;
                }

                HologramPageData pageData = type.createPageData();
                pageData.deserialize(getter);
                HologramPage page = new HologramPage(pageData);
                hologram.addPage(page);
            }
            AxHologramsAPI.getInstance().getRegistry().register(hologram);
        }
    }
}
