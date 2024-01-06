package network.something.somepotter.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;
import network.something.somepotter.spell.Spell;

public class SpellCastEvent<T extends Spell> extends Event {

    public ServerPlayer player;
    public T spell;

    public static class Pre<T extends Spell> extends SpellCastEvent<T> {

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    public static class Post<T extends Spell> extends SpellCastEvent<T> {
    }

    public boolean is(String spellId) {
        return this.spell.getId().equals(spellId);
    }

}
