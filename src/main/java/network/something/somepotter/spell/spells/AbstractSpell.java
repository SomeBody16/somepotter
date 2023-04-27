package network.something.somepotter.spell.spells;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.type.SpellType;
import network.something.somepotter.spell.type.SpellTypes;
import network.something.somepotter.util.SpellColor;

public abstract class AbstractSpell {

    public static class Event {
        public static void cast(AbstractSpell spell, LivingEntity caster) {
            var event = new SpellCastEvent(spell.id, caster);
            MinecraftForge.EVENT_BUS.post(event);
        }

        public static void onHitEntity(AbstractSpell spell, LivingEntity caster, EntityHitResult hitResult) {
            var event = new SpellHitEntityEvent(spell.id, caster, hitResult);
            MinecraftForge.EVENT_BUS.post(event);
        }

        public static void onHitBlock(AbstractSpell spell, LivingEntity caster, BlockHitResult hitResult) {
            var event = new SpellHitBlockEvent(spell.id, caster, hitResult);
            MinecraftForge.EVENT_BUS.post(event);
        }
    }

    private final String id;

    public AbstractSpell(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract SpellColor getColor();

    public abstract int getCooldown();

    public SpellType getType() {
        return SpellTypes.CHARM;
    }

    public ParticleOptions getParticle() {
        return getColor().getParticle();
    }

    public SoundEvent getSound() {
        return SoundEvents.FIRECHARGE_USE;
    }
}
