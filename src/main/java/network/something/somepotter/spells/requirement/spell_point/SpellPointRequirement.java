package network.something.somepotter.spells.requirement.spell_point;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.integration.Integrations;
import network.something.somepotter.mechanics.spell_point.SpellPointData;
import network.something.somepotter.spells.requirement.Requirement;

public class SpellPointRequirement extends Requirement {
    public static final String ID = "skill_point";

    public static SpellPointRequirement of(int cost) {
        if (Integrations.THE_VAULT.isLoaded()) {
            cost *= 10;
        }

        return new SpellPointRequirement(cost);
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
            var available = Minecraft.getInstance().player.experienceLevel;
            var displayCost = cost;
            if (Integrations.THE_VAULT.isLoaded()) {
                displayCost = (int) Math.floor(displayCost / 10F);
                available = (int) Math.floor(SpellPointData.get(Minecraft.getInstance().player) / 10F);
            }
            return new TranslatableComponent("spell.requirement.skill_point", available, displayCost);
        } catch (Exception e) {
            return new TextComponent("ERROR");
        }
    }

    @Override
    public boolean isMet(ServerPlayer player) {
        if (Integrations.THE_VAULT.isLoaded()) {
            return SpellPointData.get(player) >= cost;
        }

        return player.experienceLevel >= (cost * 10);
    }

    @Override
    public void perform(ServerPlayer player) {
        if (Integrations.THE_VAULT.isLoaded()) {
            var points = SpellPointData.get(player);
            SpellPointData.set(player, points - cost);
            return;
        }

        player.giveExperienceLevels(-(cost * 10));
    }
}
