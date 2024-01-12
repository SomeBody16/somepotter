package network.something.somepotter.spell.engorgio;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile_or_self.ProjectileOrSelfCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EngorgioSpell extends Spell {

    public static final String ID = "engorgio";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<EngorgioSpell> getListener() {
        return new EngorgioListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileOrSelfCast();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(20, 10, 80, 50);
        gesture.draw.line(80, 50, 20, 90);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }
}
