package network.something.somepotter.wand;

import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.init.SpellInit;

import static net.minecraft.Util.NIL_UUID;

public class SpellChosenPacket implements ServerSideHandler {

    String spellId;

    public SpellChosenPacket(String spellId) {
        this.spellId = spellId;
    }

    @Override
    public void handle(ServerPlayer serverPlayer) {
        var spell = SpellInit.getSpell(spellId);
        var text = new TextComponent(spell.getId())
                .withStyle(style -> style.withColor(spell.getColor().getRGB24()));

        serverPlayer.sendMessage(text, ChatType.GAME_INFO, NIL_UUID);
        ChosenSpells.setChosenSpell(serverPlayer, spellId);
    }
}
