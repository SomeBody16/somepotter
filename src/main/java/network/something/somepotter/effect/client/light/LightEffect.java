package network.something.somepotter.effect.client.light;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import com.lowdragmc.shimmer.client.light.LightManager;
import com.mojang.math.Vector3f;

public abstract class LightEffect {

    public int duration, ticks;
    protected ColorPointLight light;

    public LightEffect(int duration, Vector3f pos, int color, float radius, boolean uv) {
        this.duration = duration;
        this.ticks = 0;
        this.light = LightManager.INSTANCE.addLight(pos, color, radius, uv);
    }

    public void tick() {
        ticks++;
    }

    public void onExpire() {
        light.remove();
    }

}
