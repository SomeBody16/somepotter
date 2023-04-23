package network.something.somepotter.spell.tickable.core;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public abstract class SpellTickable {
    protected LivingEntity caster;
    protected int duration;
    protected ServerLevel level;

    public SpellTickable(LivingEntity caster, int duration) {
        if (caster.level.isClientSide) {
            throw new RuntimeException("Cannot create spell-tickable client side");
        }
        this.caster = caster;
        this.duration = duration;
        this.level = (ServerLevel) caster.level;
    }

    public abstract void tick();

    public boolean isExpired() {
        return this.duration-- <= 0;
    }
}
