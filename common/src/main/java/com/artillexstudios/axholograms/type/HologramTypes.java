package com.artillexstudios.axholograms.type;

import com.artillexstudios.axapi.utils.logging.LogUtils;
import com.artillexstudios.axholograms.api.holograms.type.HologramType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HologramTypes implements com.artillexstudios.axholograms.api.holograms.type.HologramTypes {
    private final Map<String, HologramType<?>> typeRegistry = new ConcurrentHashMap<>();

    @Override
    public HologramType<?> register(@NotNull HologramType<?> type) {
        String lowercase = type.getName().toLowerCase(Locale.ENGLISH);
        if (this.typeRegistry.containsKey(lowercase)) {
            LogUtils.warn("Failed to register type with identifier {} as it is already registered!", lowercase);
            return null;
        }

        this.typeRegistry.put(lowercase, type);
        return type;
    }

    @Override
    public void deregister(@NotNull HologramType<?> type) {
        String lowercase = type.getName().toLowerCase(Locale.ENGLISH);
        if (!this.typeRegistry.containsKey(lowercase)) {
            LogUtils.warn("Failed to deregister type with identifier {} as it is not registered!", lowercase);
            return;
        }

        this.typeRegistry.remove(lowercase);
    }

    @Override
    public HologramType<?> getByString(@NotNull String name) {
        return this.typeRegistry.get(name.toLowerCase(Locale.ENGLISH));
    }

    @Override
    public Collection<HologramType<?>> registered() {
        return Collections.unmodifiableCollection(this.typeRegistry.values());
    }

    @Override
    public Set<String> names() {
        return Collections.unmodifiableSet(this.typeRegistry.keySet());
    }
}
