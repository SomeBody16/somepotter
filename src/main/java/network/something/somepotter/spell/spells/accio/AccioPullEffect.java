package network.something.somepotter.spell.spells.accio;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SomePotter.MOD_ID)
public class AccioPullEffect {
    private static class Entry {
        public final ItemEntity item;
        public final LivingEntity target;
        public int ticksLeft;

        public Entry(ItemEntity item, LivingEntity target, int ticksLeft) {
            this.item = item;
            this.target = target;
            this.ticksLeft = ticksLeft;
        }

        public boolean isExpired() {
            return ticksLeft <= 0;
        }
    }

    private static final List<Entry> ENTRIES = new ArrayList<>();

    public static void add(ItemEntity item, LivingEntity target, int duration) {
        var entry = new Entry(item, target, duration);
        ENTRIES.add(entry);
    }

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent event) {
        ENTRIES.forEach(entry -> {
            pull(entry.item, entry.target);
            entry.ticksLeft--;
        });
        ENTRIES.removeIf(Entry::isExpired);
    }

    protected static void pull(ItemEntity item, LivingEntity target) {
        var motion = target.getEyePosition()
                .subtract(item.getEyePosition());

        item.setDeltaMovement(motion.normalize().scale(0.2));
    }


}
