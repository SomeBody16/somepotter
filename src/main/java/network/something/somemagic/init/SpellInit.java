package network.something.somemagic.init;

import net.minecraft.world.entity.LivingEntity;
import network.something.somemagic.spell.*;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SpellInit {

    protected static final Map<String, Class<? extends Spell>> SPELLS = new HashMap<>();

    public static void register(Class<? extends Spell> spellClass) {
        try {
            var id = spellClass.getDeclaredField("ID").get(null).toString();
            SPELLS.put(id, spellClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static {
        register(AccioSpell.class);
        register(WingardiumLeviosaSpell.class);
        register(BombardaSpell.class);
        register(AvadaKedavraSpell.class);
    }

    public static Spell getSpell(String spellName, LivingEntity caster) {
        if (!SPELLS.containsKey(spellName)) {
            return new Spell(spellName, caster);
        }
        try {
            Class<? extends Spell> spellClass = SPELLS.get(spellName);
            Constructor<? extends Spell> spellConstructor = spellClass.getConstructor(LivingEntity.class);
            return spellConstructor.newInstance(caster);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
