package network.something.somepotter.spell.type;

import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.type.charm.CharmType;
import network.something.somepotter.spell.type.counter.CounterType;
import network.something.somepotter.spell.type.curse.CurseType;
import network.something.somepotter.spell.type.healing.HealingType;
import network.something.somepotter.spell.type.hex.HexType;
import network.something.somepotter.spell.type.jinx.JinxType;
import network.something.somepotter.spell.type.transfiguration.TransfigurationType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpellTypes {
    protected static final List<SpellType> TYPES = new ArrayList<>();

    public static CharmType CHARM = register(new CharmType());
    public static CounterType COUNTER = register(new CounterType());
    public static CurseType CURSE = register(new CurseType());
    public static HealingType HEALING = register(new HealingType());
    public static HexType HEX = register(new HexType());
    public static JinxType JINX = register(new JinxType());
    public static TransfigurationType TRANSFIGURATION = register(new TransfigurationType());


    public static @Nullable SpellType get(String typeId) {
        return TYPES.stream()
                .filter(t -> Objects.equals(t.getId(), typeId))
                .findFirst()
                .orElse(null);
    }

    private static <TSpell extends SpellType> TSpell register(TSpell spellType) {
        TYPES.add(spellType);
        SomePotter.LOGGER.info("Registered spellType: {}", spellType.getId());
        return spellType;
    }

    public static List<SpellType> asList() {
        return TYPES;
    }

}
