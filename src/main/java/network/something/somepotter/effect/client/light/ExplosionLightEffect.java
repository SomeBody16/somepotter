package network.something.somepotter.effect.client.light;

import com.mojang.math.Vector3f;

public class ExplosionLightEffect extends LightEffect {

    protected final float maxRadius;

    public ExplosionLightEffect(int duration, Vector3f pos, int color, float radius, boolean uv) {
        super(duration, pos, color, 0.1F, uv);
        this.maxRadius = radius;
    }

    @Override
    public void tick() {
        super.tick();

        light.radius = calculateRadius(duration, ticks);
        light.update();
    }

    protected float calculateRadius(int totalDurationTicks, int ticksPassed) {
        // Calculate the phase where the radius increases quickly
        var increasePhaseTicks = totalDurationTicks * 0.2F;

        // Calculate the radius based on the number of ticks passed
        if (ticksPassed <= increasePhaseTicks) {
            // During the increase phase, radius grows linearly to its max value
            return maxRadius * (ticksPassed / increasePhaseTicks);
        } else {
            // After reaching max radius, it linearly decreases to 0
            var decreasePhaseTicks = totalDurationTicks - increasePhaseTicks;
            var ticksInDecreasePhase = ticksPassed - increasePhaseTicks;
            return maxRadius * (1 - (ticksInDecreasePhase / decreasePhaseTicks));
        }
    }
}
