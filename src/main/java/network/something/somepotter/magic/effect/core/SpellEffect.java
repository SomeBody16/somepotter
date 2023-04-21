package network.something.somepotter.magic.effect.core;

import net.minecraft.world.level.Level;
import network.something.somepotter.magic.spell.core.Spell;

public abstract class SpellEffect {
    protected Spell spell;
    protected int duration;
    protected Level level;

    public SpellEffect(Spell spell, int duration) {
        this.spell = spell;
        this.duration = duration;
        this.level = spell.caster.level;
    }

    public abstract void tick();

    public boolean isExpired() {
        return this.duration-- <= 0;
    }

}
