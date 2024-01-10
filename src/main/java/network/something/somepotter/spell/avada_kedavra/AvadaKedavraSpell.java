package network.something.somepotter.spell.avada_kedavra;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.Spell;
import network.something.somepotter.spell.SpellListener;
import network.something.somepotter.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

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
    public ColorUtil getColor() {
        return ColorUtil.UNFORGIVEABLE;
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
