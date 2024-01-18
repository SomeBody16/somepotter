package network.something.somepotter.spells.cast;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.spells.spell.Spell;

public abstract class Cast {

    public LivingEntity caster;
    public ServerLevel level;
    public Spell spell;

    protected int abilityPower;
    protected float areaOfEffect;

    public abstract String getId();

    public abstract void execute();

    public Cast populate(SpellCastEvent.Post<?> event) {
        return this
                .setCaster(event.caster)
                .setLevel(event.level)
                .setSpell(event.spell)
                .setAbilityPower(event.abilityPower)
                .setAreaOfEffect(event.areaOfEffect);
    }

    public Cast setCaster(LivingEntity caster) {
        this.caster = caster;
        return this;
    }

    public Cast setLevel(ServerLevel level) {
        this.level = level;
        return this;
    }

    public Cast setSpell(Spell spell) {
        this.spell = spell;
        return this;
    }

    public Cast setAbilityPower(int abilityPower) {
        this.abilityPower = abilityPower;
        return this;
    }

    public Cast setAreaOfEffect(float areaOfEffect) {
        this.areaOfEffect = areaOfEffect;
        return this;
    }
}
