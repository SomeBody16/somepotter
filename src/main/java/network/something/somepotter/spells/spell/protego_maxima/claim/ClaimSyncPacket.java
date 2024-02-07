package network.something.somepotter.spells.spell.protego_maxima.claim;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.Util.NIL_UUID;

public class ClaimSyncPacket implements ServerSideHandler, ClientSideHandler {
    protected Map<Integer, Map<Integer, Claim>> claims = new HashMap<>();

    // ClientSideHandler
    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle() {
        ClaimClient.update(claims);
    }

    // ServerSideHandler
    @Override
    public void handle(ServerPlayer player) {
        Claim.Data.get((ServerLevel) player.level).forEach((pos, claim) -> {
            claims.computeIfAbsent(pos.x, k -> new HashMap<>());

            var clone = new Claim();
            clone.pos = claim.pos;
            clone.owner = Claim.hasAccess((ServerLevel) player.level, pos, player) ? player.getUUID() : NIL_UUID;

            claims.get(pos.x).put(pos.z, clone);
        });
        sendToClient(player);
    }
}
