package network.something.somepotter.spell.api.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Event;
import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.spell.spells.Spells;

public class SpellCastEvent extends Event {

    private final String spellId;
    private final LivingEntity caster;

    public SpellCastEvent(String spellId, LivingEntity caster) {
        this.spellId = spellId;
        this.caster = caster;
    }

    public String getSpellId() {
        return spellId;
    }

    public LivingEntity getCaster() {
        return caster;
    }

    public AbstractSpell getSpell() {
        return Spells.get(spellId);
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
