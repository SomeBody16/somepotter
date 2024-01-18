package network.something.somepotter.spells.spell.confringo;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;

public class ConfringoListener extends SpellListener<ConfringoSpell> {

    @Override
    public void onSpellHitBlock(SpellHitEvent.Post<ConfringoSpell> event, BlockHitResult hitResult) {
        event.level.explode(
                event.caster,
                event.spell.getDamageSource(event.caster),
                null,
                hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z,
                event.areaOfEffect,
                true,
                Explosion.BlockInteraction.NONE
        );
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<ConfringoSpell> event, EntityHitResult hitResult, Entity entity) {
        event.level.explode(
                event.caster,
                event.spell.getDamageSource(event.caster),
                null,
                hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z,
                event.areaOfEffect,
                true,
                Explosion.BlockInteraction.NONE
        );
    }
}
