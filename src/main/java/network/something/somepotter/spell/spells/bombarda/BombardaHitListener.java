package network.something.somepotter.spell.spells.bombarda;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

import java.util.List;

public class BombardaHitListener extends SpellHitListener {
    public BombardaHitListener() {
        super(List.of(
                BombardaSpell.BOMBARDA.getId(),
                BombardaSpell.BOMBARDA_MAXIMA.getId()
        ));
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        if (event.getSpell() instanceof BombardaSpell bombardaSpell) {
            explode(level, event.getLocation().add(0, -0.5f, 0), bombardaSpell);
        }
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        if (event.getSpell() instanceof BombardaSpell bombardaSpell) {
            explode(level, event.getLocation(), bombardaSpell);
        }
    }

    public void explode(ServerLevel level, Vec3 pos, BombardaSpell spell) {
        level.explode(
                null,
                pos.x, pos.y, pos.z,
                spell.explosionSize,
                false,
                Explosion.BlockInteraction.DESTROY
        );
    }
}
