package network.something.somemagic.magic.effect.core;

import net.minecraft.world.phys.AABB;
import network.something.somemagic.magic.spell.core.Spell;

public abstract class AreaOfEffectSpellEffect extends SpellEffect {

    public final AABB areaOfEffect;

    public AreaOfEffectSpellEffect(Spell spell, AABB areaOfEffect, int duration) {
        super(spell, duration);
        this.areaOfEffect = areaOfEffect;
    }
}
