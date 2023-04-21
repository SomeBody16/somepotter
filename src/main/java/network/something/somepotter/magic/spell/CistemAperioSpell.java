package network.something.somepotter.magic.spell;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.phys.BlockHitResult;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.magic.spell.core.TouchSpell;
import network.something.somepotter.util.SpellColor;

public class CistemAperioSpell extends TouchSpell {
    public static final String ID = "cistem_aperio";

    public CistemAperioSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 15;
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
        var level = spellEntity.level;
        var blockState = level.getBlockState(hitResult.getBlockPos());

        if (blockState.getBlock() instanceof ChestBlock
                && caster instanceof Player player
        ) {
            blockState.use(level, player, InteractionHand.MAIN_HAND, hitResult);
        }
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }
}

