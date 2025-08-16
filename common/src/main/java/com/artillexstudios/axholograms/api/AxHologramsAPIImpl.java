package com.artillexstudios.axholograms.api;

import com.artillexstudios.axholograms.AxHologramsPlugin;
import com.artillexstudios.axholograms.api.holograms.Hologram;
import com.artillexstudios.axholograms.api.holograms.type.HologramTypes;

public final class AxHologramsAPIImpl implements AxHologramsAPI {

    @Override
    public HologramTypes getTypes() {
        return AxHologramsPlugin.getInstance().getTypes();
    }


    @Override
    public Hologram createHologram() {
        return null;
    }

    @Override
    public Hologram createSaveableHologram() {
        return null;
    }
}
