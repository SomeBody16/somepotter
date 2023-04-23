package network.something.somepotter.spell.spells.fumos;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;
import network.something.somepotter.util.ParticleUtil;

public class FumosHitListener extends SpellHitListener {
    public FumosHitListener() {
        super(FumosSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        spawnSmokescreen(level, event.getLocation());
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        spawnSmokescreen(level, event.getLocation());
    }

    public void spawnSmokescreen(Level level, Vec3 pos) {
        ParticleUtil.spawn(
                level,
                ParticleTypes.CAMPFIRE_COSY_SMOKE,
                pos,
                FumosSpell.RANGE,
                FumosSpell.PARTICLES_PER_BLOCK
        );
    }
}
