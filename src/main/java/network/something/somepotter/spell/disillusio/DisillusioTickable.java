package network.something.somepotter.spell.disillusio;

import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.tickable.Tickable;

public class DisillusioTickable extends Tickable {

    protected LivingEntity target;

    public DisillusioTickable(int duration, LivingEntity target) {
        super(duration);
        this.target = target;
    }

    @Override
    public void tick() {
        super.tick();
        target.setInvisible(true);
    }

    @Override
    public void onExpired() {
        target.setInvisible(false);
    }
}
