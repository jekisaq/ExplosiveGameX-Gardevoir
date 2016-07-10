package ru.explosivegamex.gardevoir.modules.caseInformer.fireworkScripts;

import org.bukkit.Location;

import java.util.Collection;

public abstract class FireworkScript {
    protected Location defaultLocation;

    public FireworkScript(Location defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    protected abstract void setUp();
    public abstract Collection<Location> getFireworkLocations();
}
