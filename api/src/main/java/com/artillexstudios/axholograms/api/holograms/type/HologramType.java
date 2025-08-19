package com.artillexstudios.axholograms.api.holograms.type;

import com.artillexstudios.axapi.hologram.Hologram;
import com.artillexstudios.axapi.hologram.page.HologramPage;
import com.artillexstudios.axholograms.api.holograms.data.HologramPageData;

public interface HologramType<T extends HologramPageData> {

    String getName();

    HologramPage<?, ?> createHologramPage(Hologram hologram, T data);

    T createPageData();

    T createDefaultPageData();
}
