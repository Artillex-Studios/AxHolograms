package com.artillexstudios.axholograms.type;

import com.artillexstudios.axapi.hologram.Hologram;
import com.artillexstudios.axapi.hologram.page.HologramPage;
import com.artillexstudios.axapi.packetentity.meta.entity.TextDisplayMeta;
import com.artillexstudios.axholograms.api.holograms.type.HologramType;
import com.artillexstudios.axholograms.data.TextHologramPageData;

public class TextHologramType implements HologramType<TextHologramPageData> {

    @Override
    public String getName() {
        return "text";
    }

    @Override
    public HologramPage<?, ?> createHologramPage(Hologram hologram, TextHologramPageData data) {
        var page = hologram.createPage(com.artillexstudios.axapi.hologram.HologramTypes.TEXT);
        TextDisplayMeta meta = (TextDisplayMeta) page.getEntityMeta();
        data.apply(page);
        data.setChangeListener(() -> {
            meta.resetSilently();
            data.apply(page);
        });
        return page;
    }


    @Override
    public TextHologramPageData createPageData() {
        return new TextHologramPageData();
    }
}
