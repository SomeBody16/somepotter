package network.something.somepotter.spells.spell.bombarda;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;

public class BombardaListener extends SpellListener<BombardaSpell> {

    @Override
    public void onSpellHitBlock(SpellHitEvent.Post<BombardaSpell> event, BlockHitResult hitResult) {
        event.level.explode(
                event.caster,
                event.spell.getDamageSource(event.caster),
                null,
                hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z,
                event.areaOfEffect, false, event.spell.getBlockInteraction()
        );
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<BombardaSpell> event, EntityHitResult hitResult, Entity entity) {
        event.level.explode(
                event.caster,
                event.spell.getDamageSource(event.caster),
                null,
                hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z,
                event.areaOfEffect, false, event.spell.getBlockInteraction()
        );
    }
}
