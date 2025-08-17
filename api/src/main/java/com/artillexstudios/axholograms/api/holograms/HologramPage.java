package com.artillexstudios.axholograms.api.holograms;

import com.artillexstudios.axholograms.api.holograms.data.HologramPageData;

public interface HologramPage {

    HologramPageData getData();

    com.artillexstudios.axapi.hologram.page.HologramPage<?, ?> getBackingPage();
}
