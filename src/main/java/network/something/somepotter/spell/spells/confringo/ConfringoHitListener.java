package network.something.somepotter.spell.spells.confringo;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

import java.util.List;

public class ConfringoHitListener extends SpellHitListener {
    public ConfringoHitListener() {
        super(ConfringoSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        explode(level, event.getLocation(), event.getCaster());
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        explode(level, event.getLocation(), event.getCaster());
    }

    public void explode(ServerLevel level, Vec3 pos, LivingEntity caster) {
        var areaOfEffect = new AABB(pos, pos)
                .inflate(ConfringoSpell.SIZE);

        List<Entity> entities = level.getEntities(null, areaOfEffect);
        entities.forEach(entity -> entity.setRemainingFireTicks(20 * 10));

        level.explode(
                caster,
                ConfringoSpell.getDamageSource(caster),
                null,
                pos.x, pos.y, pos.z,
                ConfringoSpell.SIZE,
                true,
                Explosion.BlockInteraction.NONE
        );
    }
}
