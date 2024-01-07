package network.something.somepotter.event;

import net.minecraftforge.eventbus.api.Cancelable;
import network.something.somepotter.spell.Spell;

public class SpellCastEvent<T extends Spell> extends SpellEvent<T> {

    @Cancelable
    public static class Pre<T extends Spell> extends SpellCastEvent<T> {
    }

    public static class Post<T extends Spell> extends SpellCastEvent<T> {
    }

}
