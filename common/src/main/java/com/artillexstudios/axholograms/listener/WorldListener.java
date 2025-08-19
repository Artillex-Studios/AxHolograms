package com.artillexstudios.axholograms.listener;

import com.artillexstudios.axholograms.api.AxHologramsAPI;
import com.artillexstudios.axholograms.api.holograms.Hologram;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class WorldListener implements Listener {

    @EventHandler
    public void onWorldLoadEvent(WorldLoadEvent event) {
        for (Hologram savedHologram : AxHologramsAPI.getInstance().getRegistry().getSavedHolograms()) {
            if (!savedHologram.getLocation().getWorld().getName().equals(event.getWorld().getName())) {
                continue;
            }

            savedHologram.loadWithWorld();
        }
    }

    @EventHandler
    public void onWorldUnloadEvent(WorldUnloadEvent event) {
        for (Hologram savedHologram : AxHologramsAPI.getInstance().getRegistry().getSavedHolograms()) {
            if (!savedHologram.getLocation().getWorld().getName().equals(event.getWorld().getName())) {
                continue;
            }

            savedHologram.destroy();
        }
    }
}
