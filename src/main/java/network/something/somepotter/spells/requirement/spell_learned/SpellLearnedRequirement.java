package network.something.somepotter.spells.requirement.spell_learned;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.mechanics.spell_learn.SpellLearnManager;
import network.something.somepotter.spells.requirement.Requirement;

public class SpellLearnedRequirement extends Requirement {
    public static final String ID = "spell_learned";

    public static SpellLearnedRequirement of(String spellId) {
        return new SpellLearnedRequirement(spellId);
    }


    protected String spellId;

    protected SpellLearnedRequirement(String spellId) {
        this.spellId = spellId;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Component getText() {
        var spell = new TranslatableComponent("spell." + spellId);
        return new TranslatableComponent("spell.requirement.spell_learned", spell);
    }

    @Override
    public boolean isMet(ServerPlayer player) {
        return SpellLearnManager.isLearned(player, spellId);
    }
}
