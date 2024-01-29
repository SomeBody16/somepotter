package network.something.somepotter.spells.requirement.spell_point;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import network.something.somepotter.integration.Integrations;
import network.something.somepotter.mechanics.spell_point.SpellPointData;
import network.something.somepotter.spells.requirement.Requirement;

public class SpellPointRequirement extends Requirement {
    public static final String ID = "skill_point";

    public static SpellPointRequirement of(int cost) {
        return new SpellPointRequirement(cost * 10);
    }

    protected int cost;

    protected SpellPointRequirement(int cost) {
        this.cost = cost;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Component getText() {
        try {
            var available = getAvailablePoints(Minecraft.getInstance().player);
            return new TranslatableComponent("spell.requirement.skill_point", available, cost);
        } catch (Exception e) {
            return new TextComponent("ERROR");
        }
    }

    @Override
    public boolean isMet(ServerPlayer player) {
        var available = getAvailablePoints(Minecraft.getInstance().player);
        return available >= cost;
    }

    @Override
    public void perform(ServerPlayer player) {
        if (Integrations.THE_VAULT.isLoaded()) {
            var points = SpellPointData.get(player);
            SpellPointData.set(player, points - cost);
            return;
        }

        player.giveExperienceLevels(-cost);
    }

    protected int getAvailablePoints(Player player) {
        if (Integrations.THE_VAULT.isLoaded()) {
            return SpellPointData.get(player);
        }
        return player.experienceLevel;
    }
}
