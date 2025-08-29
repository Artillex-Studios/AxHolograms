package com.artillexstudios.axholograms.api.holograms;

import com.artillexstudios.axapi.utils.Location;
import com.artillexstudios.axholograms.api.holograms.data.HologramPageData;
import com.artillexstudios.axholograms.api.holograms.type.HologramType;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

public interface Hologram {

    Location getLocation();

    void setLocation(Location location);

    String getName();

    List<HologramPage> getPages();

    HologramPageData getCommonData(HologramType<?> type);

    default void addPage(HologramPage page) {
        this.addPage(this.getPages().size(), page);
    }

    void addPage(int index, HologramPage page);

    /**
     * Destroys the hologram display, but does not remove its configuration.
     */
    void destroy();

    /**
     * Destroys and deletes the hologram.
     */
    void delete();

    /**
     * Saves the data of the hologram to its file.
     */
    void save();

    boolean shouldSave();

    default boolean isLoaded() {
        return this.getBackingHologram() != null;
    }

    /**
     * Tell the Hologram, that it's world is loaded, and it can be created.
     * @return A boolean, indicating if the Hologram was loaded.
     */
    @ApiStatus.Internal
    boolean loadWithWorld();

    /**
     * Get the backing AxAPI hologram instance behind this hologram.
     * @return The AxAPI hologram this instance wraps, or null if the world this hologram
     * is in is not loaded.
     */
    @ApiStatus.Internal
    com.artillexstudios.axapi.hologram.Hologram getBackingHologram();
}
