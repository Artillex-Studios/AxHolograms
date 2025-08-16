package com.artillexstudios.axholograms.hologram;

import com.artillexstudios.axapi.utils.Location;
import com.artillexstudios.axholograms.api.holograms.HologramPage;
import com.artillexstudios.axholograms.data.HologramSaver;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hologram implements com.artillexstudios.axholograms.api.holograms.Hologram {
    private final List<HologramPage> pages = new ArrayList<>();
    private final String name;
    private final boolean shouldSave;
    private Location location;

    public Hologram(String name, Location location, boolean shouldSave) {
        this.name = name;
        this.location = Preconditions.checkNotNull(location, "Can't construct a hologram with an unknown location!");
        this.shouldSave = shouldSave;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<HologramPage> getPages() {
        return Collections.unmodifiableList(this.pages);
    }

    @Override
    public void addPage(int index, HologramPage page) {
        this.pages.add(index, page);
    }

    @Override
    public void destroy() {
        com.artillexstudios.axapi.hologram.Hologram backingHologram = this.getBackingHologram();
        if (backingHologram == null) {
            return;
        }

        backingHologram.remove();
    }

    @Override
    public void delete() {
        this.destroy();
        if (!this.shouldSave) {
            return;
        }

        // TODO: remove the file of this hologram
    }

    @Override
    public void save() {
        if (!this.shouldSave) {
            return;
        }

        HologramSaver.save(this);
    }

    @Override
    public void loadWithWorld() {

    }

    @Override
    public com.artillexstudios.axapi.hologram.Hologram getBackingHologram() {
        return null;
    }
}
