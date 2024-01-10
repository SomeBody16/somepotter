package network.something.somepotter.spell.accio;

import ca.lukegrahamlandry.lib.config.ConfigWrapper;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Summons an object towards the caster
 * <p>
 * Works like a magnet for items only
 */
public class AccioSpell extends Spell {

    public static final String ID = "accio";
    public static final Supplier<AccioConfig> CONFIG = ConfigWrapper.server(AccioConfig.class)
            .dir(SomePotter.MOD_ID)
            .named(ID);

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public float getAreaOfEffect() {
        return 1.0F;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.UTILITY;
    }

    @Override
    public @NotNull SpellListener<AccioSpell> getListener() {
        return new AccioListener();
    }
}
