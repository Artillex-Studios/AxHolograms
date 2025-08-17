package com.artillexstudios.axholograms.hologram;

import com.artillexstudios.axapi.utils.logging.LogUtils;
import com.artillexstudios.axholograms.api.holograms.Hologram;
import com.artillexstudios.axholograms.config.Config;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HologramRegistry implements com.artillexstudios.axholograms.api.holograms.HologramRegistry {
    private final Map<String, Hologram> registry = new ConcurrentHashMap<>();

    @Override
    public Hologram register(Hologram hologram) {
        String lowercase = hologram.getName().toLowerCase(Locale.ENGLISH);
        if (this.registry.containsKey(lowercase)) {
            LogUtils.warn("Failed to register hologram with identifier {} as it is already registered!", lowercase);
            return null;
        }

        this.registry.put(lowercase, hologram);
        if (Config.debug) {
            LogUtils.debug("Registered hologram {}!", hologram.getName());
        }
        return hologram;
    }

    @Override
    public void deregister(Hologram hologram) {
        String lowercase = hologram.getName().toLowerCase(Locale.ENGLISH);
        if (!this.registry.containsKey(lowercase)) {
            LogUtils.warn("Failed to deregister type with identifier {} as it is not registered!", lowercase);
            return;
        }

        this.registry.remove(lowercase);
    }

    @Override
    public @Nullable Hologram getByName(@NonNull String name) {
        return this.registry.get(name.toLowerCase(Locale.ENGLISH));
    }

    @Override
    public Collection<Hologram> registered() {
        return Collections.unmodifiableCollection(this.registry.values());
    }

    @Override
    public Collection<Hologram> getNonSavedHolograms() {
        return this.registry.values().stream()
                .filter(hologram -> !hologram.shouldSave())
                .toList();
    }

    @Override
    public Collection<Hologram> getSavedHolograms() {
        return this.registry.values().stream()
                .filter(Hologram::shouldSave)
                .toList();
    }

    @Override
    public Set<String> names() {
        return Collections.unmodifiableSet(this.registry.keySet());
    }
}
