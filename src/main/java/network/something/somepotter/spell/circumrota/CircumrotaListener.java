package network.something.somepotter.spell.circumrota;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.SpellListener;

public class CircumrotaListener extends SpellListener<CircumrotaSpell> {

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<CircumrotaSpell> event, EntityHitResult hitResult, Entity entity) {
        if (entity instanceof ServerPlayer player) {
            new CircumrotaPacket().sendToClient(player);
        } else {
            entity.rotate(Rotation.CLOCKWISE_180);
        }
    }


}
