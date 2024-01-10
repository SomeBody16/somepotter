package network.something.somepotter.wand;

import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.util.WandUtil;

public class SpellCastPacket implements ServerSideHandler {
    @Override
    public void handle(ServerPlayer serverPlayer) {
        if (!WandUtil.isPlayerHoldingWand(serverPlayer)) return;

        var spell = ChosenSpells.getChosenSpell(serverPlayer);
        spell.cast(serverPlayer);
    }
}
