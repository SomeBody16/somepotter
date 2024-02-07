package network.something.somepotter.spells.spell.protego_diabolica;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.BlockInit;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell.basic_cast.BasicCastSpell;
import network.something.somepotter.spells.spell.protego_maxima.claim.Claim;
import network.something.somepotter.spells.tickable.Tickables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.minecraft.Util.NIL_UUID;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ProtegoDiabolicaListener extends SpellListener<ProtegoDiabolicaSpell> {

    public static Set<BlockPos> listBlocksOfType(Level level, BlockPos pos, int maxCount, Block block) {
        var result = new HashSet<BlockPos>();

        var toCheck = new ArrayList<>(List.of(pos));
        var checked = new HashSet<BlockPos>();

        while (!toCheck.isEmpty() && result.size() < maxCount) {
            var toCheckPos = toCheck.remove(0);
            if (checked.contains(toCheckPos)) continue;
            checked.add(toCheckPos);

            var state = level.getBlockState(toCheckPos);
            if (state.is(block)) {
                result.add(toCheckPos);
                toCheck.addAll(getNearPositions(toCheckPos));
            }
        }

        return result;
    }

    protected static Set<BlockPos> getNearPositions(BlockPos pos) {
        return new HashSet<>(List.of(
                pos,
                pos.above(),
                pos.below(),
                pos.north(),
                pos.south(),
                pos.east(),
                pos.west()
        ));
    }

    @SubscribeEvent
    public static void onBasicCastHitShield(SpellHitEvent.Pre<BasicCastSpell> event) {
        if (!event.is(BasicCastSpell.ID)) return;

        if (event.caster instanceof ServerPlayer serverPlayer) {
            if (!Claim.hasAccess(event.level, event.hitResult.getLocation(), serverPlayer)) return;
            if (event.hitResult instanceof BlockHitResult blockHitResult) {
                var blockState = event.level.getBlockState(blockHitResult.getBlockPos());
                if (!blockState.is(BlockInit.PROTEGO_DIABOLICA.get())) return;

                event.setCanceled(true);
                blockState = blockState.setValue(ProtegoDiabolicaBlock.BLOCKING, !blockState.getValue(ProtegoDiabolicaBlock.BLOCKING));
                ProtegoDiabolicaBlock.updateShield(event.level, blockHitResult.getBlockPos(), blockState);
            }
        }
    }

    @Override
    public void onSpellHitBlock(SpellHitEvent.Post<ProtegoDiabolicaSpell> event, BlockHitResult hitResult) {
        var block = event.level.getBlockState(hitResult.getBlockPos()).getBlock();
        if (block == Blocks.AIR || block == BlockInit.PROTEGO_DIABOLICA.get()) return;

        var blocks = listBlocksOfType(event.level, hitResult.getBlockPos(), 64, block);

        var canPutShield = blocks.stream().allMatch(pos -> Claim.exists(event.level, pos));
        if (!canPutShield) {
            if (event.caster instanceof ServerPlayer player) {
                var msg = new TranslatableComponent("spell.protego_diabolica.not_claimed");
                player.sendMessage(msg, NIL_UUID);
            }
            return;
        }

        var tickable = new ProtegoDiabolicaTickable(blocks, event.level);
        Tickables.add(tickable);
    }
}
