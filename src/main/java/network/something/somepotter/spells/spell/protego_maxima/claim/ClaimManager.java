package network.something.somepotter.spells.spell.protego_maxima.claim;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ClaimManager {

    public static void init() {
        ClaimData.init();
    }

    public static void sync(Level level) {
        ClaimData.sync(level);
    }

    public static void add(Level level, ChunkPos pos, ServerPlayer player) {
        add(level, pos, player.getUUID());
    }

    public static void add(Level level, ChunkPos pos, UUID owner) {
        var claim = new Claim();
        claim.owner = owner;
        claim.chunkX = pos.x;
        claim.chunkZ = pos.z;
        claim.friends = getFriends(level, owner);
        ClaimData.set(level, pos, claim);
    }

    public static void remove(Level level, ChunkPos pos) {
        ClaimData.remove(level, pos);
    }

    public static void update(Level level, Consumer<Claim> update) {
        ClaimData.update(level, update);
    }

    public static boolean exists(Level level, Vec3 pos) {
        return exists(level, new BlockPos(pos));
    }

    public static boolean exists(Level level, BlockPos pos) {
        return exists(level, new ChunkPos(pos));
    }

    public static boolean exists(Level level, int chunkX, int chunkZ) {
        return exists(level, new ChunkPos(chunkX, chunkZ));
    }

    public static boolean exists(Level level, ChunkPos pos) {
        return ClaimData.get(level).containsKey(pos);
    }

    public static Claim get(Level level, Vec3 pos) {
        return get(level, new BlockPos(pos));
    }

    public static Claim get(Level level, BlockPos pos) {
        return get(level, new ChunkPos(pos));
    }

    public static Claim get(Level level, int chunkX, int chunkZ) {
        return get(level, new ChunkPos(chunkX, chunkZ));
    }

    public static Claim get(Level level, ChunkPos pos) {
        return ClaimData.get(level).get(pos);
    }

    public static boolean hasAccess(Level level, Player player, Vec3 pos) {
        return hasAccess(level, player, new BlockPos(pos));
    }

    public static boolean hasAccess(Level level, Player player, BlockPos pos) {
        return hasAccess(level, player, new ChunkPos(pos));
    }

    public static boolean hasAccess(Level level, Player player, ChunkPos pos) {
        return !exists(level, pos) || get(level, pos).hasAccess(player);
    }

    public static List<UUID> getFriends(Level level, Player owner) {
        return getFriends(level, owner.getUUID());
    }

    public static List<UUID> getFriends(Level level, UUID owner) {
        return ClaimData.get(level).values().stream()
                .filter(claim -> claim.owner.equals(owner))
                .findFirst()
                .orElse(new Claim())
                .friends;
    }

    public static void addFriend(Level level, ServerPlayer owner, ServerPlayer friend) {
        addFriend(level, owner.getUUID(), friend.getUUID());
    }

    public static void addFriend(Level level, UUID owner, UUID friend) {
        update(level, claim -> {
            if (claim.owner.equals(owner)) {
                claim.friends.add(friend);
                claim.save(level);
            }
        });
    }

    public static void removeFriend(Level level, ServerPlayer owner, ServerPlayer friend) {
        removeFriend(level, owner.getUUID(), friend.getUUID());
    }

    public static void removeFriend(Level level, UUID owner, UUID friend) {
        update(level, claim -> {
            if (claim.owner.equals(owner)) {
                claim.friends.remove(friend);
                claim.save(level);
            }
        });
    }

}
