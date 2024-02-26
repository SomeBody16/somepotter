package network.something.somepotter.mechanics.spell_queue;

import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.basic_cast.BasicCastSpell;

import java.util.ArrayList;
import java.util.List;

public class SpellQueueManager {

    protected static final List<Spell> QUEUE = new ArrayList<>();

    public static void add(String spellId) {
        new SpellChosenPacket(spellId).sendToServer();

        if (spellId.equals(BasicCastSpell.ID)) return;
        if (has(spellId)) return;
        if (QUEUE.size() >= 4) shift();

        var spell = SpellInit.get(spellId);
        QUEUE.add(spell);
    }

    public static boolean has(String spellId) {
        return QUEUE.stream().anyMatch(spell -> spell.getId().equals(spellId));
    }

    public static Spell shift() {
        if (QUEUE.isEmpty()) return SpellInit.get(BasicCastSpell.ID);
        return QUEUE.remove(0);
    }

    public static void clear() {
        QUEUE.clear();
    }

    public static List<Spell> get() {
        return QUEUE;
    }

    public static void remove(String spellId) {
        QUEUE.removeIf(spell -> spell.getId().equals(spellId));
    }

}
