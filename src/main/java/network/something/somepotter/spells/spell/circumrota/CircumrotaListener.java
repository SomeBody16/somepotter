package network.something.somepotter.spells.spell.circumrota;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;

public class CircumrotaListener extends SpellListener<CircumrotaSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<CircumrotaSpell> event, EntityHitResult hitResult, Entity entity) {
        var posBehind = entity.position().add(entity.getLookAngle().scale(-1));
        entity.lookAt(EntityAnchorArgument.Anchor.EYES, posBehind);
    }

    @Override
    public void onSpellHitPlayer(SpellHitEvent.Post<CircumrotaSpell> event, EntityHitResult hitResult, ServerPlayer player) {
        new CircumrotaPacket().sendToClient(player);
    }
}
