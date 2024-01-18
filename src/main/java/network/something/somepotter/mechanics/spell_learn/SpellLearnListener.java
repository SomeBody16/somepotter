package network.something.somepotter.mechanics.spell_learn;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.spells.spell.Spell;

import static net.minecraft.Util.NIL_UUID;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpellLearnListener {

    public static void showErrorMessage(ServerPlayer player) {
        var msg = new TranslatableComponent("spell.advancement.locked")
                .withStyle(ChatFormatting.RED);
        player.sendMessage(msg, ChatType.GAME_INFO, NIL_UUID);
    }

    @SubscribeEvent
    public static void onPreCast(SpellCastEvent.Pre<Spell> event) {
        if (event.caster instanceof ServerPlayer player) {
            if (SpellLearnManager.isLearned(player, event.spell)) return;

            event.setCanceled(true);
            showErrorMessage(player);
        }
    }

}
