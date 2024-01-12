package network.something.somepotter.spell.aguamenti;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile_or_self.ProjectileOrSelfCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Creates a jet of water from the caster's wand
 * <p>
 * If cast at block, it will extinguish fire if possible, otherwise will create water source block
 * If cast at entity, it will extinguish fire if possible
 */
public class AguamentiSpell extends Spell {

    public static final String ID = "aguamenti";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public float getAreaOfEffect() {
        return 2F;
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileOrSelfCast();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.bezierCurve(5, 55, 10, 80, 30, 80, 45, 50);
        gesture.draw.bezierCurve(45, 50, 70, 20, 80, 10, 95, 50);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.UTILITY;
    }

    @Override
    public @NotNull SpellListener<AguamentiSpell> getListener() {
        return new AguamentiListener();
    }
}
