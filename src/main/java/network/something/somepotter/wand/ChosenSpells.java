package network.something.somepotter.wand;

import net.minecraft.server.level.ServerPlayer;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.basic_cast.BasicCastSpell;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChosenSpells {


    protected static final Map<UUID, Spell> chosenSpells = new HashMap<>();

    public static void setChosenSpell(ServerPlayer player, String spellId) {
        var spell = SpellInit.getSpell(spellId);
        chosenSpells.put(player.getUUID(), spell);
    }

    public static void castChosenSpell(ServerPlayer player) {
        var spell = chosenSpells.getOrDefault(player.getUUID(), SpellInit.getSpell(BasicCastSpell.ID));
        chosenSpells.remove(player.getUUID());

        spell.cast(player);
    }

}
