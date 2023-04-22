package network.something.somepotter.magic.effect.core;

import net.minecraft.server.level.ServerLevel;
import network.something.somepotter.magic.spell.core.Spell;

public abstract class SpellEffect {
    protected Spell spell;
    protected int duration;
    protected ServerLevel level;

    public SpellEffect(Spell spell, int duration) {
        if (spell.caster.level.isClientSide) {
            throw new RuntimeException("Cannot run spell-effect on client side");
        }

        this.spell = spell;
        this.duration = duration;
        this.level = (ServerLevel) spell.caster.level;
    }

    public abstract void tick();

    public boolean isExpired() {
        return this.duration-- <= 0;
    }

}
