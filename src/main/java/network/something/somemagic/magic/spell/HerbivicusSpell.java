package network.something.somemagic.magic.spell;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somemagic.entity.SpellEntity;
import network.something.somemagic.magic.spell.core.ProjectileSpell;
import network.something.somemagic.util.SpellColor;

public class HerbivicusSpell extends ProjectileSpell {
    public static final String ID = "herbivicus";

    public HerbivicusSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
        var level = spellEntity.level;
        var blockPos = hitResult.getBlockPos();
        var blockState = level.getBlockState(blockPos);

        if (blockState.getBlock() instanceof BonemealableBlock bonemealableBlock
                && level instanceof ServerLevel serverLevel
        ) {
            bonemealableBlock.performBonemeal(serverLevel, level.random, blockPos, blockState);
        }
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }

}

