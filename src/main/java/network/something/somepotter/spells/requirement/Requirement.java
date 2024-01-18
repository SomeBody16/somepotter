package network.something.somepotter.spells.requirement;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public abstract class Requirement {

    abstract public String getId();

    abstract public Component getText();

    abstract public boolean isMet(ServerPlayer player);

    public void perform(ServerPlayer player) {
    }

}
