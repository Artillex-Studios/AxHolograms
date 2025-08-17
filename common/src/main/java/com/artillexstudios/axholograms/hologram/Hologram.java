package com.artillexstudios.axholograms.hologram;

import com.artillexstudios.axapi.utils.Location;
import com.artillexstudios.axapi.utils.logging.LogUtils;
import com.artillexstudios.axholograms.api.AxHologramsAPI;
import com.artillexstudios.axholograms.api.holograms.HologramPage;
import com.artillexstudios.axholograms.api.holograms.data.HologramPageData;
import com.artillexstudios.axholograms.api.holograms.type.HologramType;
import com.artillexstudios.axholograms.config.Config;
import com.artillexstudios.axholograms.data.HologramSaver;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hologram implements com.artillexstudios.axholograms.api.holograms.Hologram {
    private final List<HologramPage> pages = new ArrayList<>();
    private final String name;
    private final boolean shouldSave;
    private com.artillexstudios.axapi.hologram.Hologram backingHologram;
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
    public void setLocation(Location location) {
        this.location = location;
        this.save();

        com.artillexstudios.axapi.hologram.Hologram backingHologram = this.getBackingHologram();
        if (backingHologram == null) {
            this.loadWithWorld();
            return;
        }

        backingHologram.teleport(location.toBukkit());
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
        com.artillexstudios.axapi.hologram.Hologram backingHologram = this.backingHologram;
        if (backingHologram == null) {
            return;
        }

        this.backingHologram = null;
        for (HologramPage page : this.pages) {
            ((com.artillexstudios.axholograms.hologram.HologramPage) page).setBackingPage(null);
        }
        backingHologram.remove();
    }

    @Override
    public void delete() {
        this.destroy();
        AxHologramsAPI.getInstance().getRegistry().deregister(this);

        if (!this.shouldSave) {
            return;
        }

        this.pages.clear();
        HologramSaver.delete(this);
    }

    @Override
    public void save() {
        if (!this.shouldSave) {
            return;
        }

        HologramSaver.save(this);
    }

    @Override
    public boolean shouldSave() {
        return this.shouldSave;
    }

    @Override
    public boolean loadWithWorld() {
        if (this.backingHologram != null || this.location.world().toBukkit() == null) {
            return false;
        }

        if (Config.debug) {
            LogUtils.debug("Loading hologram with world!");
        }
        this.backingHologram = new com.artillexstudios.axapi.hologram.Hologram(this.location.toBukkit());
        for (HologramPage page : this.pages) {
            com.artillexstudios.axapi.hologram.page.HologramPage<?, ?> hologramPage = ((HologramType<HologramPageData>) page.getData().getType())
                    .createHologramPage(this.backingHologram, page.getData());

            ((com.artillexstudios.axholograms.hologram.HologramPage) page).setBackingPage(hologramPage);
            hologramPage.spawn();
        }

        return true;
    }

    @Override
    public com.artillexstudios.axapi.hologram.Hologram getBackingHologram() {
        return this.backingHologram;
    }
}
