package network.something.somepotter.spell.spells;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.client.KeyInit;
import network.something.somepotter.init.MessageInit;
import network.something.somepotter.packet.PacketCastSpell;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SpellKeyInit {

    private static final Map<KeyMapping, AbstractSpell> SPELLS = new HashMap<>();

    public static void register() {
        Spells.forEach((spell) -> {
            var spellKey = KeyInit.key(spell.getId(), KeyInit.Category.SPELL);
            SPELLS.put(spellKey, spell);
            ClientRegistry.registerKeyBinding(spellKey);
        });
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            SPELLS.forEach(((keyMapping, spell) -> {
                if (keyMapping.consumeClick()) {
                    var castPacket = new PacketCastSpell(spell.getId());
                    MessageInit.sendToServer(castPacket);
                }
            }));
        }
    }

}
