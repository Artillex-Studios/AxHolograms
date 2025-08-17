package com.artillexstudios.axholograms.hologram;

import com.artillexstudios.axholograms.api.holograms.data.HologramPageData;

public class HologramPage implements com.artillexstudios.axholograms.api.holograms.HologramPage {
    private final HologramPageData data;
    private com.artillexstudios.axapi.hologram.page.HologramPage<?, ?> backingPage;

    public HologramPage(HologramPageData data) {
        this.data = data;
    }

    @Override
    public HologramPageData getData() {
        return this.data;
    }

    public void setBackingPage(com.artillexstudios.axapi.hologram.page.HologramPage<?, ?> page) {
        this.backingPage = page;
    }

    @Override
    public com.artillexstudios.axapi.hologram.page.HologramPage<?, ?> getBackingPage() {
        return this.backingPage;
    }
}
