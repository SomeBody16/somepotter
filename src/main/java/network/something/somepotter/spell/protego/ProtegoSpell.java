package network.something.somepotter.spell.protego;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.self.SelfCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Creates a magical barrier that deflects minor to moderate hexes, jinxes, and curses
 * <p>
 * Will work short time and will be able to deflect only small spells
 */
public class ProtegoSpell extends Spell {

    public static final String ID = "protego";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ProtegoSpell> getListener() {
        return new ProtegoListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new SelfCast();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(50, 90, 50, 10);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public ColorUtil getColor() {
        return new ColorUtil(0x00FFFF);
    }
}
