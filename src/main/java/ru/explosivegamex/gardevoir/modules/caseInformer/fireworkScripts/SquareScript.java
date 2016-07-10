package ru.explosivegamex.gardevoir.modules.caseInformer.fireworkScripts;

import com.google.common.collect.Lists;
import org.bukkit.Location;

import java.util.List;

public final class SquareScript extends FireworkScript {
    private List<Location> locations = Lists.newLinkedList();

    public SquareScript(Location defaultLocation) {
        super(defaultLocation);

        setUp();
    }

    /**
     * This method calls on creating object
     */
    private void setUp() {
        locations.add(defaultLocation.clone().add(-3, 0 , -3));
        locations.add(defaultLocation.clone().add(3, 0 , -3));
        locations.add(defaultLocation.clone().add(3, 0 , 3));
        locations.add(defaultLocation.clone().add(-3, 0 , 3));
    }

    @Override
    public List<Location> getFireworkLocations() {
        return locations;
    }
}
