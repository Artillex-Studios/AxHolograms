package com.artillexstudios.axholograms.hologram;

import com.artillexstudios.axapi.config.adapters.MapConfigurationGetter;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Hologram implements com.artillexstudios.axholograms.api.holograms.Hologram {
    private final Map<HologramType<?>, HologramPageData> commonData = new HashMap<>();
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
    public HologramPageData getCommonData(HologramType<?> type) {
        HologramPageData data = this.commonData.computeIfAbsent(type, HologramType::createPageData);
        data.setChangeListener(() -> {
            Map<String, Object> serialized = new LinkedHashMap<>();
            data.serialize(serialized);
            MapConfigurationGetter getter = new MapConfigurationGetter(serialized);
            for (HologramPageData value : this.commonData.values()) {
                value.deserialize(getter);
            }

            for (HologramPage page : this.pages) {
                page.getData().deserialize(getter);
            }
        });
        return data;
    }

    @Override
    public void addPage(int index, HologramPage page) {
        this.pages.add(index, page);
        if (this.backingHologram != null) {
            com.artillexstudios.axapi.hologram.page.HologramPage<?, ?> hologramPage = ((HologramType<HologramPageData>) page.getData().getType())
                    .createHologramPage(this.backingHologram, page.getData());

            ((com.artillexstudios.axholograms.hologram.HologramPage) page).setBackingPage(hologramPage);
            this.backingHologram.addPage(hologramPage);
            hologramPage.spawn();
        }
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
        if (this.backingHologram != null || this.location.getWorld().toBukkit() == null) {
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
