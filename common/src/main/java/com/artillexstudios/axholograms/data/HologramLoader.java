package com.artillexstudios.axholograms.data;

import com.artillexstudios.axapi.config.YamlConfiguration;
import com.artillexstudios.axapi.libs.snakeyaml.DumperOptions;
import com.artillexstudios.axapi.utils.Location;
import com.artillexstudios.axholograms.api.serializer.LocationSerializer;
import com.artillexstudios.axholograms.utils.FileUtils;

import java.io.File;
import java.util.Collection;

public class HologramLoader {

    public static void loadAll() {
        Collection<File> files = org.apache.commons.io.FileUtils.listFiles(FileUtils.HOLOGRAMS_DIRECTORY.toFile(), new String[]{".yml", ".yaml"}, true);

        for (File file : files) {
            YamlConfiguration<?> configuration = YamlConfiguration.of(file.toPath())
                    .withDumperOptions(options -> {
                        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                        options.setPrettyFlow(true);
                    })
                    .build();

            configuration.load();

            Location location = LocationSerializer.INSTANCE.deserialize(configuration.getString("location"));
        }
    }
}
