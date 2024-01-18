package network.something.somepotter.mechanics.spell_queue;

import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.util.WandUtil;

public class SpellCastPacket implements ServerSideHandler {

    protected String spellId;

    public SpellCastPacket(String spellId) {
        this.spellId = spellId;
        SomePotter.LOGGER.info("Casting spell: " + spellId);
    }

    @Override
    public void handle(ServerPlayer serverPlayer) {
        if (!WandUtil.isPlayerHoldingWand(serverPlayer)) return;
        SpellInit.get(spellId).cast(serverPlayer);
    }
}
