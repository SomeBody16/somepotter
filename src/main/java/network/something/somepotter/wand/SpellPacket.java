package network.something.somepotter.wand;

import ca.lukegrahamlandry.lib.network.ServerSideHandler;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.init.SpellInit;

public class SpellPacket implements ServerSideHandler {

    String pronunciation;

    public SpellPacket(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    @Override
    public void handle(ServerPlayer serverPlayer) {
        serverPlayer.sendMessage(new TextComponent("Spell: " + this.pronunciation), serverPlayer.getUUID());

        var spellId = this.pronunciation.replace(" ", "_").toLowerCase();
        var spell = SpellInit.getSpell(spellId);

        var spellCastEventPre = new SpellCastEvent.Pre<>();
        spellCastEventPre.player = serverPlayer;
        spellCastEventPre.spell = spell;

        var cancelled = MinecraftForge.EVENT_BUS.post(spellCastEventPre);
        if (cancelled) return;

        var spellCastEventPost = new SpellCastEvent.Post<>();
        spellCastEventPost.player = serverPlayer;
        spellCastEventPost.spell = spell;
        MinecraftForge.EVENT_BUS.post(spellCastEventPost);
    }
}
