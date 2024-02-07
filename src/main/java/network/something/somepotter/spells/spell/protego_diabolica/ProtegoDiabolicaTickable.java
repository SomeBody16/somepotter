package network.something.somepotter.spells.spell.protego_diabolica;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import network.something.somepotter.init.BlockInit;
import network.something.somepotter.spells.tickable.Tickable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProtegoDiabolicaTickable extends Tickable {

    protected List<BlockPos> blocks;
    protected ServerLevel level;

    public ProtegoDiabolicaTickable(Set<BlockPos> blocks, ServerLevel level) {
        super(blocks.size() * 10);
        this.blocks = new ArrayList<>(List.copyOf(blocks));
        this.level = level;
    }

    @Override
    public void tick() {
        if (duration++ % 20 != 0) return;
        if (blocks.isEmpty()) return;
        duration = 0;

        var block = blocks.remove(0);
        var state = BlockInit.PROTEGO_DIABOLICA.get().defaultBlockState();
        level.setBlockAndUpdate(block, state);
    }

    @Override
    public boolean isExpired() {
        return blocks.isEmpty();
    }
}
