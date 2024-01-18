package network.something.somepotter.mechanics.spell_queue;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.mechanics.spell_learn.SpellLearnListener;
import network.something.somepotter.mechanics.spell_learn.SpellLearnManager;

import static net.minecraft.Util.NIL_UUID;

public class SpellChosenPacket implements ServerSideHandler, ClientSideHandler {

    protected String spellId;

    public SpellChosenPacket(String spellId) {
        this.spellId = spellId;
    }

    // When spell is chosen by client
    @Override
    public void handle(ServerPlayer serverPlayer) {
        if (!SpellLearnManager.isLearned(serverPlayer, spellId)) {
            SpellLearnListener.showErrorMessage(serverPlayer);
            this.sendToClient(serverPlayer);
            return;
        }

        var spell = SpellInit.get(spellId);
        var text = new TranslatableComponent("spell." + spell.getId())
                .withStyle(style -> style.withColor(spell.getColor().getRGB24()));
        serverPlayer.sendMessage(text, ChatType.GAME_INFO, NIL_UUID);
    }

    // Cannot choose this spell
    @Override
    public void handle() {
        SpellQueueManager.remove(spellId);
    }
}
