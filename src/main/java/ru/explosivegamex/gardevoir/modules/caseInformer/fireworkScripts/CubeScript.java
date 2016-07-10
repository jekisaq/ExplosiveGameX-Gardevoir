package ru.explosivegamex.gardevoir.modules.caseInformer.fireworkScripts;

import com.google.common.collect.Lists;
import org.bukkit.Location;

import java.util.List;

public class CubeScript implements FireworkScript {
    private List<Location> locations = Lists.newLinkedList();

    public CubeScript(Location defaultLocation) {

        for (int i = -3; i <= 3; i++) {
            locations.add(defaultLocation.clone().add(i, 0 , -3));
        }

        for (int i = -3; i <= 3; i++) {
            locations.add(defaultLocation.clone().add(3, 0 , i));
        }

        for (int i = 3; i >= -3; i--) {
            locations.add(defaultLocation.clone().add(i, 0 , 3));
        }

        for (int i = 3; i >= -3; i--) {
            locations.add(defaultLocation.clone().add(3, 0 , i));
        }
    }

    @Override
    public List<Location> getFireworkLocations() {
        return locations;
    }
}
