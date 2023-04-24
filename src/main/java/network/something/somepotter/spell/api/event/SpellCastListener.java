package network.something.somepotter.spell.api.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.Util.NIL_UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SomePotter.MOD_ID)
public class SpellCastListener {

    private static final Map<String, List<SpellCastListener>> LISTENERS = new HashMap<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void handle(SpellCastEvent event) {
        var spellId = event.getSpellId();
        if (!LISTENERS.containsKey(spellId)) {
            return;
        }

        SomePotter.LOGGER.info("'{}' casted '{}'", event.getCaster().getDisplayName().getString(), event.getSpellId());
        var message = new TextComponent(spellId)
                .withStyle(ChatFormatting.GREEN)
                .withStyle(ChatFormatting.ITALIC);
        event.getCaster().sendMessage(message, NIL_UUID);

        event.getCaster().level.playSound(null, event.getCaster(), event.getSpell().getSound(),
                SoundSource.PLAYERS, 1, 1);

        var listeners = LISTENERS.getOrDefault(spellId, new ArrayList<>());
        listeners.forEach(listener -> listener.onSpellCast(event));
    }

    public SpellCastListener(String spellId) {
        var listeners = LISTENERS.getOrDefault(spellId, new ArrayList<>());
        listeners.add(this);
        LISTENERS.put(spellId, listeners);
    }

    public SpellCastListener(List<String> spellIds) {
        spellIds.forEach(spellId -> {
            var listeners = LISTENERS.getOrDefault(spellId, new ArrayList<>());
            listeners.add(this);
            LISTENERS.put(spellId, listeners);
        });
    }

    public void onSpellCast(SpellCastEvent event) {
    }
}
