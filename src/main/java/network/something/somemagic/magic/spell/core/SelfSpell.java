package network.something.somemagic.magic.spell.core;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public abstract class SelfSpell extends Spell {

    protected SelfSpell(String name, LivingEntity caster) {
        super(name, caster);
    }

    public abstract void onCast();

    @Override
    public void cast() {
        super.cast();
        playParticles();
        onCast();
    }

    protected void playParticles() {
        var pos = caster.getEyePosition();
        var level = (ServerLevel) caster.level;
        level.sendParticles(
                getColor().getParticle(),
                pos.x, pos.y, pos.z,
                25,
                1, 1, 1,
                0.01
        );
    }
}
