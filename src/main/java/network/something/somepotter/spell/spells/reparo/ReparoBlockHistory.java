package network.something.somepotter.spell.spells.reparo;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ReparoBlockHistory {

    public record Entry(BlockPos pos, BlockState state) {
    }

    private static final List<Entry> history = new ArrayList<>();

    public static List<Entry> entriesFrom(AABB area) {
        var result = new ArrayList<Entry>();

        history.forEach(entry -> {
            if (area.contains(entry.pos.getX(), entry.pos.getY(), entry.pos.getZ())) {
                result.add(entry);
            }
        });
        history.removeIf(result::contains);

        return result;
    }

    private static void addEntry(Level level, BlockPos pos) {
        var state = level.getBlockState(pos);
        var entry = new Entry(pos, state);

        if (shouldSkipEntry(level, entry)) {
            return;
        }

        history.removeIf(historyEntry -> historyEntry.pos.equals(pos));
        history.add(entry);

        int maxHistorySize;
        if (level.getServer() == null) {
            maxHistorySize = ReparoSpell.ENTRIES_PER_PLAYER;
        } else {
            maxHistorySize = level.getServer().getPlayerCount() * ReparoSpell.ENTRIES_PER_PLAYER;
        }
        if (history.size() > maxHistorySize) {
            history.remove(0);
        }
    }

    @SubscribeEvent
    public static void onExplode(ExplosionEvent.Detonate event) {
        event.getExplosion().getToBlow().forEach(blockPos -> {
            addEntry(
                    event.getWorld(),
                    blockPos
            );
        });
    }

    public static boolean shouldSkipEntry(Level level, Entry entry) {
        return level.getBlockEntity(entry.pos) != null
                || entry.state.is(Blocks.AIR);
    }
}
