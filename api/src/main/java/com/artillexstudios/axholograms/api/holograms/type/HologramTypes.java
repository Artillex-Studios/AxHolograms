package com.artillexstudios.axholograms.api.holograms.type;

import com.artillexstudios.axholograms.api.AxHologramsAPI;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Set;

public interface HologramTypes {
    Supplier<HologramType<?>> TEXT = Suppliers.memoize(() ->  AxHologramsAPI.getInstance().getHologramTypeByName("text"));
    Supplier<HologramType<?>> DROPPED_ITEM = Suppliers.memoize(() ->  AxHologramsAPI.getInstance().getHologramTypeByName("dropped_item"));

    @Nullable
    HologramType<?> register(@NonNull HologramType<?> type);

    void deregister(@NonNull HologramType<?> type);

    @Nullable
    HologramType<?> getByString(@NonNull String name);

    Collection<HologramType<?>> registered();

    Set<String> names();
}
