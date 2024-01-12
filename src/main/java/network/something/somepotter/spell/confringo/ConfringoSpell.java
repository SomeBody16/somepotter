package network.something.somepotter.spell.confringo;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Explodes the target (small explosion)
 * <p>
 * Will not destroy blocks
 * Small damage, cause fire (this fire will deal more damage)
 */
public class ConfringoSpell extends Spell {
    public static final String ID = "confringo";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ConfringoSpell> getListener() {
        return new ConfringoListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public float getAreaOfEffect() {
        return 4.0F;
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(20, 20, 80, 20);
        gesture.draw.line(80, 20, 20, 80);
        gesture.draw.line(20, 80, 80, 80);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }
}
