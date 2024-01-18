package network.something.somepotter.spells.requirement.y_position;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.spells.requirement.Requirement;

public class YPositionRequirement extends Requirement {
    public static final String ID = "y_position";

    public static YPositionRequirement above(int y) {
        return new YPositionRequirement(true, y);
    }

    public static YPositionRequirement below(int y) {
        return new YPositionRequirement(false, y);
    }

    protected boolean above;
    protected int y;

    protected YPositionRequirement(boolean above, int y) {
        this.above = above;
        this.y = y;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Component getText() {
        if (above) {
            return new TranslatableComponent("spell.requirement.y_position.above", y);
        }
        return new TranslatableComponent("spell.requirement.y_position.below", y);
    }

    @Override
    public boolean isMet(ServerPlayer player) {
        if (above) {
            return player.getOnPos().getY() >= y;
        }
        return player.getOnPos().getY() <= y;
    }
}
