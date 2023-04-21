package network.something.somepotter.magic.spell;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.magic.spell.core.ProjectileSpell;
import network.something.somepotter.util.SpellColor;

public class IncendioSpell extends ProjectileSpell {
    public static final String ID = "incendio";

    public IncendioSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 5;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        hitResult.getEntity().setRemainingFireTicks(20 * 10);

    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
        var level = spellEntity.level;
        var blockPos = hitResult.getBlockPos();
        var blockState = level.getBlockState(blockPos);
        var direction = hitResult.getDirection();

        if (blockState.getBlock() instanceof TntBlock) {
            blockState.getBlock().onCaughtFire(blockState, level, blockPos, direction, caster);

        } else if (!CampfireBlock.canLight(blockState)
                && !CandleBlock.canLight(blockState)
                && !CandleCakeBlock.canLight(blockState)
        ) {
            var targetPos = blockPos.relative(direction);
            if (BaseFireBlock.canBePlacedAt(level, targetPos, caster.getDirection())) {
                playSound(SoundEvents.FLINTANDSTEEL_USE, targetPos, SoundSource.BLOCKS);
                var fireBlockState = BaseFireBlock.getState(level, targetPos);
                level.setBlock(targetPos, fireBlockState, 11);
                level.gameEvent(caster, GameEvent.BLOCK_PLACE, blockPos);
            }
        } else {
            playSound(SoundEvents.FLINTANDSTEEL_USE, blockPos, SoundSource.BLOCKS);
            level.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, Boolean.TRUE), 11);
            level.gameEvent(caster, GameEvent.BLOCK_PLACE, blockPos);
        }
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }

}

