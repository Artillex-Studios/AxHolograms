package com.artillexstudios.axholograms.api;

import com.artillexstudios.axholograms.api.holograms.Hologram;
import com.artillexstudios.axholograms.api.holograms.HologramRegistry;
import com.artillexstudios.axholograms.api.holograms.data.HologramPageData;
import com.artillexstudios.axholograms.api.holograms.type.HologramType;
import com.artillexstudios.axholograms.api.holograms.type.HologramTypes;
import net.kyori.adventure.util.Services;

public interface AxHologramsAPI {

    HologramTypes getTypes();

    default HologramType<?> getHologramTypeByName(String name) {
        return this.getTypes().getByString(name);
    }

    default <T extends HologramPageData, Z extends HologramType<T>> Z registerHologramType(Z type) {
        this.getTypes().register(type);
        return type;
    }

    HologramRegistry getRegistry();

    Hologram createHologram();

    Hologram createSaveableHologram();

    static AxHologramsAPI getInstance() {
        return Holder.INSTANCE;
    }

    final class Holder {
        private static final AxHologramsAPI INSTANCE = Services.service(AxHologramsAPI.class).orElseThrow();
    }
}
