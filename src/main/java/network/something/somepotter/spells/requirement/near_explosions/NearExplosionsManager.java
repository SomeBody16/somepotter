package network.something.somepotter.spells.requirement.near_explosions;

import net.minecraft.core.Position;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NearExplosionsManager {

    protected static final Map<Date, Vec3> EXPLOSIONS = new HashMap<>();

    public static int countInRange(Position pos, double range) {
        clean();
        return (int) EXPLOSIONS.values().stream()
                .filter(explosionPos -> explosionPos.closerThan(pos, range))
                .count();
    }


    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Detonate event) {
        clean();
        EXPLOSIONS.put(new Date(), event.getExplosion().getPosition());
    }

    /**
     * Remove explosions older than 5 seconds
     */
    protected static void clean() {
        EXPLOSIONS.entrySet().removeIf(entry -> {
            var date = entry.getKey();
            var now = new Date();
            var diff = now.getTime() - date.getTime();
            return diff > 5 * 1000;
        });
    }

}
