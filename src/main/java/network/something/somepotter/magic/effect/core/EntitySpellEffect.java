package network.something.somepotter.magic.effect.core;

import net.minecraft.world.entity.Entity;
import network.something.somepotter.magic.spell.core.Spell;

public abstract class EntitySpellEffect extends SpellEffect {

    public final Entity target;

    public EntitySpellEffect(Spell spell, Entity target, int duration) {
        super(spell, duration);
        this.target = target;
    }
}
