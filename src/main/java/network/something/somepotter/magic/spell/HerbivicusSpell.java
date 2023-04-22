package network.something.somepotter.magic.spell;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.magic.spell.core.ProjectileSpell;
import network.something.somepotter.util.SpellColor;

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

        var boneMeal = new ItemStack(Items.BONE_MEAL, 64);
        if (caster instanceof Player player) {
            if (BoneMealItem.applyBonemeal(boneMeal, level, blockPos, player)) {
                level.levelEvent(1505, blockPos, 0); // particles
            } else {
                var relativePos = blockPos.relative(hitResult.getDirection());
                var flag = level.getBlockState(blockPos).isFaceSturdy(level, blockPos, hitResult.getDirection());
                if (flag && BoneMealItem.growWaterPlant(boneMeal, level, relativePos, hitResult.getDirection())) {
                    level.levelEvent(1505, relativePos, 0); // particles
                }
            }
        }

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

