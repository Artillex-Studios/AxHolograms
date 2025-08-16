package com.artillexstudios.axholograms.api.holograms.data;

import com.artillexstudios.axapi.config.adapters.MapConfigurationGetter;
import com.artillexstudios.axapi.hologram.page.HologramPage;
import com.artillexstudios.axholograms.api.holograms.type.HologramType;

import java.util.Map;

public interface HologramPageData {

    HologramType<?> getType();

    void serialize(Map<String, Object> map);

    void deserialize(MapConfigurationGetter data);

    void apply(HologramPage<?, ?> page);

    void setChangeListener(Runnable changed);
}
