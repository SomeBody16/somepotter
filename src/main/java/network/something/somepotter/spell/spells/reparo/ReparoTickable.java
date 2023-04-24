package network.something.somepotter.spell.spells.reparo;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.api.particle.SpellParticle;
import network.something.somepotter.spell.tickable.core.AreaOfEffectSpellTickable;

import java.util.Comparator;
import java.util.List;

public class ReparoTickable extends AreaOfEffectSpellTickable {
    protected final List<ReparoBlockHistory.Entry> toRepair;
    protected int age = 0;

    public ReparoTickable(LivingEntity caster, AABB areaOfEffect) {
        super(caster, areaOfEffect, 0);
        toRepair = ReparoBlockHistory.entriesFrom(areaOfEffect);
        SomePotter.LOGGER.info("Started reparo - {} entries in {}", toRepair.size(), areaOfEffect);
    }

    protected void sortToRepair() {
        toRepair.sort(Comparator.comparingInt(a -> a.pos().getY()));
    }

    @Override
    public void tick() {
        if (age++ % ReparoSpell.REPAIR_EACH_N_TICK != 0
                || toRepair.isEmpty()
        ) {
            return;
        }

        sortToRepair();
        while (!toRepair.isEmpty()) {
            var entry = toRepair.remove(0);
            var pos = entry.pos();

            if (!level.getBlockState(pos).is(Blocks.AIR)) {
                continue;
            }

            level.setBlock(pos, entry.state(), 1 | 2 | 8);

            var placeSound = entry.state().getSoundType(level, pos, null).getPlaceSound();
            level.playSound(null, pos, placeSound, SoundSource.BLOCKS, 1, 1);
            SpellParticle.playTouchParticles(ReparoSpell.ID, level, new Vec3(pos.getX(), pos.getY(), pos.getZ()));

            var areaNear = new AABB(pos).inflate(ReparoSpell.RANGE);
            var closeEntries = ReparoBlockHistory.entriesFrom(areaNear);
            toRepair.addAll(closeEntries);

            return;
        }
    }

    @Override
    public boolean isExpired() {
        return toRepair.isEmpty()
                || caster.getEyePosition().distanceTo(areaOfEffect.getCenter()) > 32;
    }
}
