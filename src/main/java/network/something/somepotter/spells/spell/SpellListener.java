package network.something.somepotter.spells.spell;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;

public abstract class SpellListener<T extends Spell> {

    public void preSpellCast(SpellCastEvent.Pre<T> event) {
    }

    public void onSpellCast(SpellCastEvent.Post<T> event) {
        event.spell.getCast()
                .populate(event)
                .execute();
    }

    public void preSpellHitBlock(SpellHitEvent.Pre<T> event, BlockHitResult hitResult) {
    }

    public void onSpellHitBlock(SpellHitEvent.Post<T> event, BlockHitResult hitResult) {
    }

    public void preSpellHitEntity(SpellHitEvent.Pre<T> event, EntityHitResult hitResult, Entity entity) {
    }

    public void onSpellHitEntity(SpellHitEvent.Post<T> event, EntityHitResult hitResult, Entity entity) {
    }

    public void preSpellHitPlayer(SpellHitEvent.Pre<T> event, EntityHitResult hitResult, ServerPlayer player) {
        preSpellHitEntity(event, hitResult, player);
    }

    public void onSpellHitPlayer(SpellHitEvent.Post<T> event, EntityHitResult hitResult, ServerPlayer player) {
        onSpellHitEntity(event, hitResult, player);
    }
}
