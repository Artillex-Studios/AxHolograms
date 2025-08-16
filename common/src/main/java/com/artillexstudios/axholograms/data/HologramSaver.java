package com.artillexstudios.axholograms.data;

import com.artillexstudios.axapi.config.YamlConfiguration;
import com.artillexstudios.axapi.libs.snakeyaml.DumperOptions;
import com.artillexstudios.axholograms.api.holograms.Hologram;
import com.artillexstudios.axholograms.api.holograms.HologramPage;
import com.artillexstudios.axholograms.api.serializer.LocationSerializer;
import com.artillexstudios.axholograms.utils.FileUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class HologramSaver {

    public static void save(Hologram hologram) {
        Path configPath = FileUtils.HOLOGRAMS_DIRECTORY.resolve(hologram.getName() + ".yml");
        YamlConfiguration<?> configuration = YamlConfiguration.of(configPath)
                .withDumperOptions(options -> {
                    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                    options.setPrettyFlow(true);
                })
                .build();

        configuration.load();
        configuration.set("location", LocationSerializer.INSTANCE.serialize(hologram.getLocation()));
        List<Map<String, Object>> pagesConfiguration = new ArrayList<>();

        for (HologramPage page : hologram.getPages()) {
            Map<String, Object> pageConfiguration = new LinkedHashMap<>();
            pageConfiguration.put("type", page.getData().getType().getName());
            page.getData().serialize(pageConfiguration);
            pagesConfiguration.add(pageConfiguration);
        }

        configuration.set("pages", pagesConfiguration);
    }
}
