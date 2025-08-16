package com.artillexstudios.axholograms.api.holograms;

import com.artillexstudios.axapi.utils.Location;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

public interface Hologram {

    Location getLocation();

    String getName();

    List<HologramPage> getPages();

    default void addPage(HologramPage page) {
        this.addPage(this.getPages().size() - 1, page);
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

    /**
     * Tell the Hologram, that it's world is loaded, and it can be created.
     */
    @ApiStatus.Internal
    void loadWithWorld();

    /**
     * Get the backing AxAPI hologram instance behind this hologram.
     * @return The AxAPI hologram this instance wraps, or null if the world this hologram
     * is in is not loaded.
     */
    @ApiStatus.Internal
    com.artillexstudios.axapi.hologram.Hologram getBackingHologram();
}
