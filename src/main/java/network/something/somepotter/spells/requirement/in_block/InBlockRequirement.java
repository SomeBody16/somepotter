package network.something.somepotter.spells.requirement.in_block;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.util.TranslatableComponentUtil;

public class InBlockRequirement extends Requirement {
    public static final String ID = "in_block";

    public static InBlockRequirement of(ResourceLocation blockId) {
        return new InBlockRequirement(blockId);
    }

    protected ResourceLocation blockId;

    protected InBlockRequirement(ResourceLocation blockId) {
        this.blockId = blockId;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Component getText() {
        var blockName = TranslatableComponentUtil.block(blockId);
        return new TranslatableComponent("spell.requirement.in_block", blockName);
    }

    @Override
    public boolean isMet(ServerPlayer player) {
        var blockPlayerIsInside = player.level.getBlockState(player.blockPosition()).getBlock();
        return blockPlayerIsInside.getRegistryName() == blockId;
    }
}
