package network.something.somemagic.magic;

import net.minecraft.world.entity.LivingEntity;
import network.something.somemagic.SomeMagic;
import network.something.somemagic.magic.spell.*;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Spells {

    protected static final Map<String, Class<? extends Spell>> SPELLS = new HashMap<>();

    private static void register(Class<? extends Spell> spellClass) {
        try {
            var id = spellClass.getDeclaredField("ID").get(null).toString();
            SPELLS.put(id, spellClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static {
        register(AccioSpell.class);
        register(AvadaKedavraSpell.class);
        register(BombardaSpell.class);
        register(WingardiumLeviosaSpell.class);
    }

    public static Spell getSpell(String spellName, LivingEntity caster) {
        if (!hasSpell(spellName)) {
            return new UnknownSpell(caster);
        }
        try {
            Class<? extends Spell> spellClass = SPELLS.get(spellName);
            Constructor<? extends Spell> spellConstructor = spellClass.getConstructor(LivingEntity.class);
            return spellConstructor.newInstance(caster);
        } catch (Exception e) {
            SomeMagic.LOGGER.error("Cannot retrieve spell {} by {}", spellName, caster.getDisplayName());
            SomeMagic.LOGGER.error("Exception", e);
            return new UnknownSpell(caster);
        }
    }

    public static boolean hasSpell(String spellName) {
        return SPELLS.containsKey(spellName);
    }

}
