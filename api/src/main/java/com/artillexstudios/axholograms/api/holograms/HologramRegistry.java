package com.artillexstudios.axholograms.api.holograms;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Set;

public interface HologramRegistry {

    Hologram register(Hologram hologram);

    void deregister(Hologram hologram);

    @Nullable
    Hologram getByName(@NonNull String name);

    Collection<Hologram> registered();

    Collection<Hologram> getNonSavedHolograms();

    Collection<Hologram> getSavedHolograms();

    Set<String> names();
}
