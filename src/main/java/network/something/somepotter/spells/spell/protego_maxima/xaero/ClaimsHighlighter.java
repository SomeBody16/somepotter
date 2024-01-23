package network.something.somepotter.spells.spell.protego_maxima.xaero;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import network.something.somepotter.spells.spell.protego_maxima.claim.Claim;
import network.something.somepotter.spells.spell.protego_maxima.claim.ClaimClient;
import xaero.map.highlight.ChunkHighlighter;

import java.util.List;

import static net.minecraft.Util.NIL_UUID;

public class ClaimsHighlighter extends ChunkHighlighter {
    public ClaimsHighlighter() {
        super(false);
    }

    @Override
    protected int[] getColors(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
        var currentClaim = ClaimClient.get(chunkX, chunkZ);
        if (currentClaim == null) {
            return null;
        }

        var topClaim = ClaimClient.get(chunkX, chunkZ - 1);
        var rightClaim = ClaimClient.get(chunkX + 1, chunkZ);
        var bottomClaim = ClaimClient.get(chunkX, chunkZ + 1);
        var leftClaim = ClaimClient.get(chunkX - 1, chunkZ);

        var isOwner = !currentClaim.owner.equals(NIL_UUID);
        int centerColor = isOwner ? 0x00FF0077 : 0x0000FF77;
        int sideColor = isOwner ? 0x00FF00CC : 0x0000FFCC;

        this.resultStore[0] = centerColor;
        this.resultStore[1] = topClaim == null ? sideColor : centerColor;
        this.resultStore[2] = rightClaim == null ? sideColor : centerColor;
        this.resultStore[3] = bottomClaim == null ? sideColor : centerColor;
        this.resultStore[4] = leftClaim == null ? sideColor : centerColor;
        return this.resultStore;
    }

    @Override
    public Component getChunkHighlightSubtleTooltip(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
        var claim = ClaimClient.get(chunkX, chunkZ);
        if (claim == null) return null;

        if (claim.owner.equals(NIL_UUID)) {
            return new TranslatableComponent("spell.protego_maxima.claim.access_denied")
                    .withStyle(ChatFormatting.RED);
        }

        return new TranslatableComponent("spell.protego_maxima.claim.added")
                .withStyle(ChatFormatting.GREEN);
    }

    @Override
    public Component getChunkHighlightBluntTooltip(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
        return null;
    }

    @Override
    public int calculateRegionHash(ResourceKey<Level> dimension, int regionX, int regionZ) {
        if (!allowedDimension(dimension)) return 0;

        var accumulator = 0L;
        for (int x = 0; x < 32; x++) {
            for (int z = 0; z < 32; z++) {
                // Calculate chunk coordinates
                int chunkX = regionX * 32 + x;
                int chunkZ = regionZ * 32 + z;

                // Check if the chunk is claimed and get the claim
                if (ClaimClient.isClaimed(chunkX, chunkZ)) {
                    var claim = ClaimClient.get(chunkX, chunkZ);

                    if (claim != null) {
                        // Incorporate claim details into the hash
                        accumulator = updateHash(accumulator, claim);
                    }
                }
            }
        }

        return (int) (accumulator >> 32) ^ (int) accumulator;
    }

    private long updateHash(long accumulator, Claim claim) {
        // Update the hash based on claim's position and owner
        accumulator = 31 * accumulator + claim.pos.hashCode();
        accumulator = 31 * accumulator + (claim.owner != null ? claim.owner.hashCode() : 0);
        return accumulator;
    }

    @Override
    public boolean regionHasHighlights(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
        return allowedDimension(dimension);
    }

    @Override
    public boolean chunkIsHighlit(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
        return allowedDimension(dimension)
                && ClaimClient.isClaimed(chunkX, chunkZ);
    }

    @Override
    public void addMinimapBlockHighlightTooltips(List<Component> list, ResourceKey<Level> dimension, int blockX, int blockZ, int width) {
    }

    protected boolean allowedDimension(ResourceKey<Level> dimension) {
        return dimension.equals(Level.OVERWORLD);
    }
}
