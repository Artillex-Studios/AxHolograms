package com.artillexstudios.axholograms;

import com.artillexstudios.axapi.AxPlugin;
import com.artillexstudios.axapi.dependencies.DependencyManagerWrapper;
import com.artillexstudios.axapi.utils.featureflags.FeatureFlags;
import com.artillexstudios.axholograms.api.AxHologramsAPI;
import com.artillexstudios.axholograms.api.holograms.Hologram;
import com.artillexstudios.axholograms.command.AxHologramsCommand;
import com.artillexstudios.axholograms.config.Config;
import com.artillexstudios.axholograms.config.Language;
import com.artillexstudios.axholograms.data.HologramLoader;
import com.artillexstudios.axholograms.hologram.HologramRegistry;
import com.artillexstudios.axholograms.listener.WorldListener;
import com.artillexstudios.axholograms.type.HologramTypes;
import com.artillexstudios.axholograms.type.TextHologramType;
import org.bukkit.Bukkit;

public final class AxHologramsPlugin extends AxPlugin {
    private static AxHologramsPlugin instance;
    private final HologramTypes types = new HologramTypes();
    private final HologramRegistry registry = new HologramRegistry();

    @Override
    public void updateFlags() {
        instance = this;
        Config.reload();
        FeatureFlags.PACKET_ENTITY_TRACKER_ENABLED.set(true);
        FeatureFlags.HOLOGRAM_UPDATE_TICKS.set(Config.hologramUpdateTicks);
    }

    @Override
    public void dependencies(DependencyManagerWrapper manager) {
        manager.dependency("dev{}jorel:commandapi-bukkit-shade:10.1.2", true);
        manager.relocate("dev{}jorel{}commandapi", "com.artillexstudios.axholograms.libs.commandapi");
    }

    @Override
    public void load() {
        Language.reload();
        AxHologramsAPI.getInstance().registerHologramType(new TextHologramType());
        AxHologramsCommand.onLoad(this);
    }

    @Override
    public void enable() {
        HologramLoader.loadAll();

        for (Hologram savedHologram : AxHologramsAPI.getInstance().getRegistry().getSavedHolograms()) {
            savedHologram.loadWithWorld();
        }
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
        AxHologramsCommand.register();
        AxHologramsCommand.onEnable();
    }

    @Override
    public void disable() {
        AxHologramsCommand.onDisable();
    }

    public static AxHologramsPlugin getInstance() {
        return instance;
    }

    public HologramTypes getTypes() {
        return this.types;
    }

    public HologramRegistry getRegistry() {
        return this.registry;
    }
}