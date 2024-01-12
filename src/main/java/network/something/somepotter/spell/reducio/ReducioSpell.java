package network.something.somepotter.spell.reducio;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile_or_self.ProjectileOrSelfCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReducioSpell extends Spell {
    public static final String ID = "reducio";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ReducioSpell> getListener() {
        return new ReducioListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileOrSelfCast();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(80, 10, 20, 50);
        gesture.draw.line(20, 50, 80, 90);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }
}
