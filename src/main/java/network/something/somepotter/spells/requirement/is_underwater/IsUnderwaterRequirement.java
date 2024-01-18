package network.something.somepotter.spells.requirement.is_underwater;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.spells.requirement.Requirement;

public class IsUnderwaterRequirement extends Requirement {
    public static final String ID = "is_underwater";

    public static IsUnderwaterRequirement of() {
        return new IsUnderwaterRequirement();
    }

    protected IsUnderwaterRequirement() {
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Component getText() {
        return new TranslatableComponent("spell.requirement.is_underwater");
    }

    @Override
    public boolean isMet(ServerPlayer player) {
        return player.isUnderWater();
    }
}
