package network.something.somepotter.spells.spell.basic_cast;

import net.minecraft.world.item.ItemStack;
import network.something.somepotter.init.ItemInit;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import network.something.somepotter.util.ConfigUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BasicCastSpell extends Spell {

    public static final String ID = "basic_cast";
    public static final Supplier<BasicCastConfig> CONFIG = ConfigUtil.server(BasicCastConfig.class, ID);

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Color getColor() {
        return new Color(0xFF0000);
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(CharmType.ID);
    }

    @Override
    public @NotNull SpellListener<BasicCastSpell> getListener() {
        return new BasicCastListener();
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(ItemInit.WAND.get());
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var result = new ArrayList<SpellGesture>();

        for (var spell : SpellInit.all()) {
            if (spell.getId().equals(ID)) {
                continue;
            }
            result.addAll(spell.getGestures());
        }

        return result;
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

}
