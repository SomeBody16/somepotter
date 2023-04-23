package network.something.somepotter.spell.spells.avada_kedavra;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class AvadaKedavraHitListener extends SpellHitListener {
    public AvadaKedavraHitListener() {
        super(AvadaKedavraSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            var damageSource = AvadaKedavraSpell.getDamageSource(event.getCaster());
            livingEntity.hurt(damageSource, 9999.0F);
            playKillSound(level, event.getEntity().getOnPos());
        }
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
    }

    protected void playKillSound(LevelAccessor pLevel, BlockPos pPos) {
        SoundEvent soundevent = SoundEvents.WITHER_DEATH;
        pLevel.playSound(null, pPos, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
