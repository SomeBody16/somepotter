package network.something.somepotter.spell.avada_kedavra;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import network.something.somepotter.cast.Cast;
import network.something.somepotter.cast.projectile.ProjectileCast;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.gesture.SpellGesture;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.spell_type.SpellType;
import network.something.somepotter.spell_type.curse.CurseType;
import network.something.somepotter.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Kills the target
 */
public class AvadaKedavraSpell extends Spell {

    public static final String ID = "avada_kedavra";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull() SpellListener<AvadaKedavraSpell> getListener() {
        return new AvadaKedavraListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(45, 10, 20, 55);
        gesture.draw.line(20, 55, 80, 45);
        gesture.draw.line(80, 45, 45, 90);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return null;
    }

    @Override
    public ColorUtil getColor() {
        return ColorUtil.UNFORGIVEABLE;
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(CurseType.ID);
    }

    @Override
    public void playHitSound(SpellHitEvent.Post<Spell> event) {
        var sound = SoundEvents.WITHER_DEATH;
        var pos = event.hitResult.getLocation();
        event.level.playSound(
                null,
                pos.x, pos.y, pos.z,
                sound,
                SoundSource.PLAYERS,
                0.8F, 1.0F
        );
    }
}
