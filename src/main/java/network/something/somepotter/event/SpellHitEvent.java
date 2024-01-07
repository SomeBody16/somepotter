package network.something.somepotter.event;

import net.minecraft.world.phys.HitResult;
import net.minecraftforge.eventbus.api.Cancelable;
import network.something.somepotter.spell.Spell;

public class SpellHitEvent<T extends Spell> extends SpellEvent<T> {

    public HitResult hitResult;

    @Cancelable
    public static class Pre<T extends Spell> extends SpellHitEvent<T> {
    }

    public static class Post<T extends Spell> extends SpellHitEvent<T> {
    }

}
