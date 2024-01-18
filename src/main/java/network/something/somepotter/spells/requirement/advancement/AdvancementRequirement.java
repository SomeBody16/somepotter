package network.something.somepotter.spells.requirement.advancement;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.util.TranslatableComponentUtil;

public class AdvancementRequirement extends Requirement {
    public static final String ID = "advancement";

    public static AdvancementRequirement of(ResourceLocation advancementId) {
        return new AdvancementRequirement(advancementId);
    }

    protected ResourceLocation advancementId;

    protected AdvancementRequirement(ResourceLocation advancementId) {
        this.advancementId = advancementId;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Component getText() {
        return TranslatableComponentUtil.advancement(advancementId);
    }

    @Override
    public boolean isMet(ServerPlayer player) {
        if (player.getServer() == null) return false;

        ServerAdvancementManager manager = player.getServer().getAdvancements();
        var advancement = manager.getAdvancement(advancementId);

        if (advancement == null) return false;
        return player.getAdvancements().getOrStartProgress(advancement).isDone();
    }
}
