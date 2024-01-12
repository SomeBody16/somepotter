package network.something.somepotter.spell.protego_maxima;

import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ConfigUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

/**
 * Claims a chunk, preventing other players from breaking blocks in it
 * Every spell cast in the chunk will be reported to owners
 */
public class ProtegoMaximaSpell extends Spell {
    public static final String ID = "protego_maxima";
    public static final Supplier<ProtegoMaximaConfig> CONFIG = ConfigUtil.server(ProtegoMaximaConfig.class, ID);

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ProtegoMaximaSpell> getListener() {
        return new ProtegoMaximaListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(50, 90, 50, 10);

        gesture.nextStroke();
        gesture.draw.circle(50, 50, 45);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public void register() {
        super.register();
    }
}
