package network.something.somepotter.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class DoorUtil {

    public static void setOpen(Level level, BlockState blockState, BlockPos blockPos, boolean isOpen) {
        if (blockState.getBlock() instanceof DoorBlock doorBlock) {
            doorBlock.setOpen(null, level, blockState, blockPos, isOpen);
        }
        if (blockState.getBlock() instanceof TrapDoorBlock) {
            blockState.setValue(TrapDoorBlock.OPEN, isOpen);
            level.setBlock(blockPos, blockState, 2);
        }
    }

    /**
     * Set open state for door/trapdoor if material != metal
     */
    public static void setOpenWeak(Level level, BlockState blockState, BlockPos blockPos, boolean isOpen) {
        if (blockState.getMaterial() != Material.METAL) {
            setOpen(level, blockState, blockPos, isOpen);
        }
    }

}
