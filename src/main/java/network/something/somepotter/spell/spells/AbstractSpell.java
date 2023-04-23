package network.something.somepotter.spell.spells;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.api.event.SpellCastEvent;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.util.SpellColor;

public abstract class AbstractSpell {

    private final String id;

    public AbstractSpell(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract SpellColor getColor();

    public ParticleOptions getParticle() {
        return getColor().getParticle();
    }

    public SoundEvent getSound() {
        return SoundEvents.FIRECHARGE_USE;
    }

    public void cast(LivingEntity caster) {
        SomePotter.LOGGER.info("Spell(id={})::cast(caster = {})", getId(), caster.getDisplayName());
        var event = new SpellCastEvent(id, caster);
        MinecraftForge.EVENT_BUS.post(event);

    }

    public void onHitEntity(LivingEntity caster, EntityHitResult hitResult) {
        SomePotter.LOGGER.info("Spell(id={})::onHitEntity(caster = {}, entity = {})",
                getId(), caster.getDisplayName(), hitResult.getEntity().getDisplayName());
        var event = new SpellHitEntityEvent(id, caster, hitResult);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public void onHitBlock(LivingEntity caster, BlockHitResult hitResult) {
        SomePotter.LOGGER.info("Spell(id={})::onHitBlock(caster = {}, block = {})",
                getId(), caster.getDisplayName(), hitResult.getLocation());
        var event = new SpellHitBlockEvent(id, caster, hitResult);
        MinecraftForge.EVENT_BUS.post(event);
    }

}
