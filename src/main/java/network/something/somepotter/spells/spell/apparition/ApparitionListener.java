package network.something.somepotter.spells.spell.apparition;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.floo.network.FlooNetworkManager;
import network.something.somepotter.floo.packet.OpenFlooNetworkScreenPacket;
import network.something.somepotter.integration.Integrations;
import network.something.somepotter.spells.spell.SpellListener;

import static net.minecraft.Util.NIL_UUID;

public class ApparitionListener extends SpellListener<ApparitionSpell> {

    @Override
    public void onSpellHitPlayer(SpellHitEvent.Post<ApparitionSpell> event, EntityHitResult hitResult, ServerPlayer player) {
        // Check for research
        if (!Integrations.THE_VAULT.loadedAndHasResearch(event.level, player, "Floo Network")) {
            return;
        }

        if (!FlooNetworkManager.isAllowedApparitionDimension(event.level)) {
            var message = new TranslatableComponent("spell.apparition.dimension_not_allowed");
            player.sendMessage(message, ChatType.GAME_INFO, NIL_UUID);
            return;
        }

        // Open GUI
        var nodes = FlooNetworkManager.listFor(player);
        new OpenFlooNetworkScreenPacket(null, nodes).sendToClient(player);
    }
}
