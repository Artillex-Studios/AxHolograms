package com.artillexstudios.axholograms;

import com.artillexstudios.axapi.AxPlugin;
import com.artillexstudios.axapi.utils.featureflags.FeatureFlags;
import com.artillexstudios.axholograms.api.AxHologramsAPI;
import com.artillexstudios.axholograms.config.Config;
import com.artillexstudios.axholograms.type.HologramTypes;
import com.artillexstudios.axholograms.type.TextHologramType;

public final class AxHologramsPlugin extends AxPlugin {
    private static AxHologramsPlugin instance;
    private final HologramTypes types = new HologramTypes();

    @Override
    public void updateFlags() {
        instance = this;
        Config.reload();
        FeatureFlags.PACKET_ENTITY_TRACKER_ENABLED.set(true);
        FeatureFlags.HOLOGRAM_UPDATE_TICKS.set(Config.hologramUpdateTicks);
    }

    @Override
    public void load() {
        AxHologramsAPI.getInstance().registerHologramType(new TextHologramType());
    }

    @Override
    public void enable() {
        super.enable();
    }

    @Override
    public void disable() {
        super.disable();
    }

    public static AxHologramsPlugin getInstance() {
        return instance;
    }

    public HologramTypes getTypes() {
        return this.types;
    }
}