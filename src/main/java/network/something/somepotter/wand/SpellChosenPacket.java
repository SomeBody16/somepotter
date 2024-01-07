package network.something.somepotter.wand;

import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

import static net.minecraft.Util.NIL_UUID;

public class SpellChosenPacket implements ServerSideHandler {

    String spellId;

    public SpellChosenPacket(String spellId) {
        this.spellId = spellId;
    }

    @Override
    public void handle(ServerPlayer serverPlayer) {
        var text = new TextComponent(spellId)
                .withStyle(ChatFormatting.GREEN);

        serverPlayer.sendMessage(text, ChatType.GAME_INFO, NIL_UUID);
        ChosenSpells.setChosenSpell(serverPlayer, spellId);
    }
}
