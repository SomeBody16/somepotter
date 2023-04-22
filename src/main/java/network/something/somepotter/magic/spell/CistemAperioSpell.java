package network.something.somepotter.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.material.Material;
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
        var blockPos = hitResult.getBlockPos();
        var blockState = level.getBlockState(blockPos);

        if (caster instanceof Player player) {
            if (blockState.getBlock() instanceof ChestBlock chestBlock) {
                var menuProvider = chestBlock.getMenuProvider(blockState, level, blockPos);
                player.openMenu(menuProvider);
            }
        }

        if (blockState.getBlock() instanceof DoorBlock doorBlock
                && blockState.getMaterial() != Material.METAL
        ) {
            doorBlock.setOpen(caster, spellEntity.level, blockState, hitResult.getBlockPos(), true);
        }
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }
}

