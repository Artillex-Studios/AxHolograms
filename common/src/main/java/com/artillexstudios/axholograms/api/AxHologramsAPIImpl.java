package com.artillexstudios.axholograms.api;

import com.artillexstudios.axapi.utils.Location;
import com.artillexstudios.axholograms.AxHologramsPlugin;
import com.artillexstudios.axholograms.api.holograms.Hologram;
import com.artillexstudios.axholograms.api.holograms.HologramRegistry;
import com.artillexstudios.axholograms.api.holograms.type.HologramTypes;

public final class AxHologramsAPIImpl implements AxHologramsAPI {

    @Override
    public HologramTypes getTypes() {
        return AxHologramsPlugin.getInstance().getTypes();
    }

    @Override
    public HologramRegistry getRegistry() {
        return AxHologramsPlugin.getInstance().getRegistry();
    }

    @Override
    public Hologram createHologram(String name, Location location) {
        return new com.artillexstudios.axholograms.hologram.Hologram(name, location, false);
    }

    @Override
    public Hologram createSaveableHologram(String name, Location location) {
        return new com.artillexstudios.axholograms.hologram.Hologram(name, location, true);
    }
}
