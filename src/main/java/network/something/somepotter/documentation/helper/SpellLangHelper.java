package network.something.somepotter.documentation.helper;

import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell_type.SpellType;

public class SpellLangHelper {

    public static String type(SpellType type) {
        return "spell.type.%s"
                .formatted(type.getId());
    }

    public static String typeDescription(SpellType type) {
        return "spell.type.%s.description"
                .formatted(type.getId());
    }

    public static String spell(Spell spell) {
        return "spell.%s"
                .formatted(spell.getId());
    }

    public static String spellDescription(Spell spell) {
        return "spell.%s.description"
                .formatted(spell.getId());
    }

}
