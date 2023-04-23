package network.something.somepotter.spell.spells.cistem_aperio;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.material.Material;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class CistemAperioHitListener extends SpellHitListener {
    public CistemAperioHitListener() {
        super(CistemAperioSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        var blockPos = event.getBlockPos();
        var blockState = level.getBlockState(blockPos);

        if (event.getCaster() instanceof Player player) {
            if (blockState.getBlock() instanceof ChestBlock chestBlock) {
                var menuProvider = chestBlock.getMenuProvider(blockState, level, blockPos);
                player.openMenu(menuProvider);
                return;
            }
        }

        if (blockState.getBlock() instanceof DoorBlock doorBlock
                && blockState.getMaterial() != Material.METAL
        ) {
            doorBlock.setOpen(event.getCaster(), level, blockState, event.getBlockPos(), true);
        }
    }
}
