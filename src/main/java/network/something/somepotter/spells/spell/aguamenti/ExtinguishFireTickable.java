package network.something.somepotter.spells.spell.aguamenti;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import network.something.somepotter.spells.tickable.Tickable;
import network.something.somepotter.util.AbilityPowerUtil;

import java.util.List;

public class ExtinguishFireTickable extends Tickable {

    protected ServerLevel level;
    protected BlockPos originPos;
    protected List<BlockPos> blocks;
    protected float chance;

    public ExtinguishFireTickable(ServerLevel level, BlockPos pos, int abilityPower, int areaOfEffect) {
        super(0);
        this.level = level;
        this.originPos = pos;
        this.chance = AbilityPowerUtil.getMultiplier(abilityPower * 2);

        var area = new AABB(pos).inflate(areaOfEffect);
        for (var x = area.minX; x <= area.maxX; x++) {
            for (var y = area.minY; y <= area.maxY; y++) {
                for (var z = area.minZ; z <= area.maxZ; z++) {
                    var blockPos = new BlockPos(x, y, z);
                    var blockState = level.getBlockState(blockPos);
                    if (blockState.getBlock() instanceof BaseFireBlock
                            && level.random.nextFloat() < this.chance) {
                        this.blocks.add(blockPos);
                    }
                }
            }
        }
        this.blocks.sort((a, b) -> {
            var r0 = a.distToCenterSqr(originPos.getX(), originPos.getY(), originPos.getZ());
            var r1 = b.distToCenterSqr(originPos.getX(), originPos.getY(), originPos.getZ());
            return Double.compare(r0, r1);
        });
    }

    @Override
    public void tick() {
        this.duration++;

        if (this.duration % 5 == 0) {
            var pos = this.blocks.remove(0);
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            AguamentiListener.playEmptySound(level, pos);
        }
    }

    @Override
    public boolean isExpired() {
        return this.blocks.isEmpty();
    }
}
