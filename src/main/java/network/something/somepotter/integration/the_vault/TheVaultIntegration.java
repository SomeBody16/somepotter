package network.something.somepotter.integration.the_vault;

import iskallia.vault.init.ModConfigs;
import iskallia.vault.world.data.PlayerResearchesData;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import network.something.somepotter.integration.Integration;
import network.something.somepotter.integration.the_vault.listener.VaultCompletedListener;

import static net.minecraft.Util.NIL_UUID;

public class TheVaultIntegration extends Integration {

    public TheVaultIntegration() {
        super("the_vault");
    }

    @Override
    protected void ifLoaded() {
        MinecraftForge.EVENT_BUS.addListener(VaultCompletedListener::onVaultLeave);
    }

    public boolean loadedAndHasResearch(ServerLevel level, ServerPlayer player, String researchName) {
        if (isLoaded()) {
            var researchData = PlayerResearchesData.get(level);
            var tree = researchData.getResearches(player);
            var research = ModConfigs.RESEARCHES.getByName(researchName);
            if (research == null || !tree.isResearched(research)) {
                var name = new TextComponent(researchName);
                name.setStyle(Style.EMPTY.withColor(0xFFFF00));
                var msg = new TranslatableComponent("overlay.requires_research.interact_block", name);
                player.sendMessage(msg, ChatType.GAME_INFO, NIL_UUID);
                return false;
            }
            return true;
        }
        return false;
    }
}
