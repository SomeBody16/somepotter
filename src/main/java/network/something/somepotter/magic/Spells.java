package network.something.somepotter.magic;

import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.SomePotter;
import network.something.somepotter.magic.spell.*;
import network.something.somepotter.magic.spell.core.Spell;
import network.something.somepotter.magic.spell.core.UnknownSpell;

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
        register(AguamentiSpell.class);
        register(AlohomoraSpell.class);
        register(AscendioSpell.class);
        register(AvadaKedavraSpell.class);
        register(BombardaSpell.class);
        register(BombardaMaximaSpell.class);
        register(CistemAperioSpell.class);
        register(ColloportusSpell.class);
        register(ConfringoSpell.class);
        register(CrucioSpell.class);
        register(DepulsoSpell.class);
        register(DisillusioSpell.class);
        register(EpiskeySpell.class);
        register(FumosSpell.class);
        register(HerbivicusSpell.class);
        register(IncendioSpell.class);
        register(Levioso.class);
        register(MeloforsSpell.class);
        register(RevelioSpell.class);
        register(StupefySpell.class);
        register(TempestSpell.class);
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
            SomePotter.LOGGER.error("Cannot retrieve spell {} by {}", spellName, caster.getDisplayName());
            SomePotter.LOGGER.error("Exception", e);
            return new UnknownSpell(caster);
        }
    }

    public static boolean hasSpell(String spellName) {
        return SPELLS.containsKey(spellName);
    }

}
