package network.something.somepotter.spell.api.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Event;
import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.spell.spells.Spells;

public class SpellHitEntityEvent extends Event {

    private final String spellId;
    private final LivingEntity caster;
    private final EntityHitResult hitResult;

    public SpellHitEntityEvent(String spellId, LivingEntity caster, EntityHitResult hitResult) {
        this.spellId = spellId;
        this.caster = caster;
        this.hitResult = hitResult;
    }

    public String getSpellId() {
        return spellId;
    }

    public LivingEntity getCaster() {
        return caster;
    }

    public EntityHitResult getHitResult() {
        return hitResult;
    }

    public Entity getEntity() {
        return hitResult.getEntity();
    }

    public Vec3 getLocation() {
        return this.hitResult.getEntity().getEyePosition();
    }

    public ServerLevel getLevel() {
        return (ServerLevel) getEntity().getLevel();
    }

    public AbstractSpell getSpell() {
        return Spells.get(spellId);
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
