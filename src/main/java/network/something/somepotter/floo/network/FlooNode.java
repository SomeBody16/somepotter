package network.something.somepotter.floo.network;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import network.something.somepotter.SomePotter;

import java.util.ArrayList;
import java.util.List;

public class FlooNode {
    public String name;
    public int x, y, z;
    public String dimension;

    public List<String> allowedPlayers = new ArrayList<>();

    public FlooNode(String name, BlockPos pos, String dimension) {
        this.name = name;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.dimension = dimension;
    }

    public boolean is(ServerLevel level, BlockPos pos) {
        return dimension.equals(level.dimension().getRegistryName().toString())
                && x == pos.getX()
                && y == pos.getY()
                && z == pos.getZ();
    }

    public BlockPos getPos() {
        return new BlockPos(x, y, z);
    }

    public ServerLevel getLevel(MinecraftServer server) {
        for (var levelKey : server.levelKeys()) {
            if (levelKey.getRegistryName().toString().equals(dimension)) {
                return server.getLevel(levelKey);
            }
        }
        return server.getLevel(ServerLevel.OVERWORLD);
    }

    public boolean equals(FlooNode other) {
        SomePotter.LOGGER.info("Comparing nodes: " + other + " " + this);
        if (other == null) return false;
        return other.x == x && other.y == y && other.z == z && other.dimension.equals(dimension);
    }

    @Override
    public String toString() {
        return "FlooNode[x=%d, y=%d, z=%d, dimension=%s]".formatted(x, y, z, dimension);
    }
}
