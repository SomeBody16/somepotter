package network.something.somepotter.cast.self;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.Spell;

public class SelfCast {

    public LivingEntity caster;
    public ServerLevel level;
    public Spell spell;

    protected int abilityPower;
    protected float areaOfEffect;

    public SelfCast(SpellCastEvent<?> event) {
        this.caster = event.caster;
        this.level = event.level;
        this.spell = event.spell;
        this.abilityPower = event.abilityPower;
        this.areaOfEffect = event.areaOfEffect;
    }

    public void execute() {
        var hitResult = new EntityHitResult(caster);
        SpellHitEvent.publish(spell, caster, level, abilityPower, areaOfEffect, hitResult);
    }
}
