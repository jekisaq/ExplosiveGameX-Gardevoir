package ru.explosivegamex.gardevoir.modules.caseInformer;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import ru.explosivegamex.gardevoir.modules.caseInformer.fireworkScripts.FireworkScript;

/**
 * Represents the core of a Fireworks show.
 *
 * @author Omniscimus
 */
public class FireworksProcessor implements Runnable {

    private static final Random random = new Random();
    private static final Type[] TYPES = {
            Type.BALL, Type.BALL_LARGE, Type.BURST, Type.STAR
    };
    private static final Color[] COLORS = {
            Color.AQUA, Color.BLUE,
            Color.FUCHSIA, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY,
            Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER,
            Color.WHITE, Color.TEAL, Color.YELLOW
    };

    private FireworkScript fireworkScript;

    public FireworksProcessor(FireworkScript fireworkScript) {
        this.fireworkScript = fireworkScript;
    }

    /**
     * Spawn one very random piece of fireworks. This will be done in a
     * scheduleSyncRepeatingTask.
     */
    @Override
    public void run() {
        fireworkScript.getFireworkLocations().forEach(this::shootFirework);
    }

    /**
     * Shoot a piece of random fireworks.
     *
     * @param loc place at the fireworks should spawn
     */
    private void shootFirework(Location loc) {


        Firework firework = (Firework) loc.getWorld()
                .spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        fireworkMeta.addEffect(
                FireworkEffect.builder()
                        .flicker(random.nextBoolean())
                        .withColor(COLORS[random.nextInt(14)])
                        .withFade(COLORS[random.nextInt(14)])
                        .with(TYPES[random.nextInt(3)])
                        .trail(random.nextBoolean())
                        .build()
        );
        fireworkMeta.setPower(random.nextInt(2) + 1);
        firework.setFireworkMeta(fireworkMeta);
    }

}