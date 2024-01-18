package network.something.somepotter.spells.requirement.near_explosions;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.spells.requirement.Requirement;

public class NearExplosionsRequirement extends Requirement {
    public static final String ID = "near_explosions";

    public static NearExplosionsRequirement of(int count, double range) {
        return new NearExplosionsRequirement(count, range);
    }

    protected int count;
    protected double range;

    protected NearExplosionsRequirement(int count, double range) {
        this.count = count;
        this.range = range;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Component getText() {
        return new TranslatableComponent("spell.requirement.near_explosions", count);
    }

    @Override
    public boolean isMet(ServerPlayer player) {
        return NearExplosionsManager.countInRange(player.position(), range) >= count;
    }
}
