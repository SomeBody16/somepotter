package network.something.somepotter.spell.spells.cistem_aperio;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;
import network.something.somepotter.util.DoorUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
            if (blockState.getBlock() instanceof ChestBlock chestBlock
                    && level.getBlockEntity(blockPos) instanceof ChestBlockEntity chestBlockEntity
            ) {
                var menuProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TextComponent("Cistem Aperio");
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
                        var menuProvider = chestBlock.getMenuProvider(blockState, level, blockPos);
                        if (menuProvider == null) {
                            return null;
                        }
                        return menuProvider.createMenu(pContainerId, pPlayerInventory, pPlayer);
                    }
                };

                player.openMenu(menuProvider);
                return;
            }
        }

        DoorUtil.setOpenWeak(level, blockState, event.getBlockPos(), true);
    }
}
