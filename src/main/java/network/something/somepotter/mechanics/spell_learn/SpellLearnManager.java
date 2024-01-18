package network.something.somepotter.mechanics.spell_learn;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.basic_cast.BasicCastSpell;

import static net.minecraft.Util.NIL_UUID;

public class SpellLearnManager {

    public static boolean isLearned(ServerPlayer player, Spell spell) {
        if (spell instanceof BasicCastSpell) return true;

        var advancement = getAdvancement(player.getServer(), spell);
        return player.getAdvancements().getOrStartProgress(advancement).isDone();
    }

    public static boolean isLearned(ServerPlayer player, String spellId) {
        var spell = SpellInit.get(spellId);
        return isLearned(player, spell);
    }

    public static void learn(ServerPlayer player, Spell spell) {
        var advancement = getAdvancement(player.getServer(), spell);
        var progress = player.getAdvancements().getOrStartProgress(advancement);
        if (progress.isDone()) {
            return;
        }

        for (var requirement : spell.getRequirements()) {
            if (!requirement.isMet(player)) return;
            requirement.perform(player);
        }

        for (var criteria : progress.getRemainingCriteria()) {
            player.getAdvancements().award(advancement, criteria);
        }

        var spellName = new TranslatableComponent("spell." + spell.getId());
        var msg = new TranslatableComponent("spell.advancement.unlocked", player.getDisplayName(), spellName)
                .withStyle(ChatFormatting.GREEN);
        player.getServer().getPlayerList().broadcastMessage(msg, ChatType.CHAT, NIL_UUID);
    }

    public static void learn(ServerPlayer player, String spellId) {
        var spell = SpellInit.get(spellId);
        learn(player, spell);
    }

    public static ResourceLocation getAdvancementId(Spell spell) {
        return new ResourceLocation(SomePotter.MOD_ID, "spell/" + spell.getId());
    }

    public static Advancement getAdvancement(MinecraftServer server, Spell spell) {
        return server.getAdvancements().getAdvancement(getAdvancementId(spell));
    }

}
