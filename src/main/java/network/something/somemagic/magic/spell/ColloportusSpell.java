package network.something.somemagic.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.phys.BlockHitResult;
import network.something.somemagic.entity.SpellEntity;
import network.something.somemagic.magic.spell.core.TouchSpell;
import network.something.somemagic.util.SpellColor;

public class ColloportusSpell extends TouchSpell {
    public static final String ID = "colloportus";

    public ColloportusSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 15;
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
        var blockState = spellEntity.level.getBlockState(hitResult.getBlockPos());

        if (blockState.getBlock() instanceof DoorBlock doorBlock) {
            doorBlock.setOpen(caster, spellEntity.level, blockState, hitResult.getBlockPos(), false);
        }
    }
}

