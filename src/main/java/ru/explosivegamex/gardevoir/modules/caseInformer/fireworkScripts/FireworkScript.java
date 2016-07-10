package ru.explosivegamex.gardevoir.modules.caseInformer.fireworkScripts;

import org.bukkit.Location;

import java.util.Collection;

public abstract class FireworkScript {
    Location defaultLocation;

    FireworkScript(Location defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public abstract Collection<Location> getFireworkLocations();
}
