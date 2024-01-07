package network.something.somepotter.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Event;
import network.something.somepotter.spell.Spell;

public abstract class SpellEvent<T extends Spell> extends Event {

    public LivingEntity caster;
    public ServerLevel level;
    public T spell;

    public int abilityPower;
    public float areaOfEffect;


    public boolean is(String spellId) {
        return this.spell.getId().equals(spellId);
    }

}
