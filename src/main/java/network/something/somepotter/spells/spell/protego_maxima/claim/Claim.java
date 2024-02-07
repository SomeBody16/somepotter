package network.something.somepotter.spells.spell.protego_maxima.claim;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Claim {

    public int chunkX, chunkZ;
    public UUID owner;
    public List<UUID> friends = new ArrayList<>();

    public ChunkPos pos() {
        return new ChunkPos(chunkX, chunkZ);
    }

    public void save(Level level) {
        ClaimManager.add(level, pos(), this.owner);
    }

    public boolean hasAccess(Player other) {
        return hasAccess(other.getUUID());
    }

    public boolean hasAccess(UUID other) {
        return owner.equals(other) || friends.contains(other);
    }

}
