package network.something.somepotter.spells.spell.incarcerous_captura;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.init.EffectInit;
import network.something.somepotter.spells.spell.SpellEffect;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IncarcerousCapturaEffect extends SpellEffect<IncarcerousCapturaSpell> {

    protected static final Map<UUID, CompoundTag> CAPTURED_ENTITIES = new HashMap<>();

    public static void captureEntity(ServerPlayer caster, LivingEntity target, int duration) {
        var nbt = new CompoundTag();
        nbt.putString("entity", EntityType.getKey(target.getType()).toString());
        target.save(nbt);
        CAPTURED_ENTITIES.put(caster.getUUID(), nbt);
        target.remove(Entity.RemovalReason.KILLED);

        var effect = new MobEffectInstance(EffectInit.INCARCEROUS_CAPTURA.get(), duration, 0, false, true, true);
        caster.addEffect(effect);
    }

    public static void releaseEntity(LivingEntity caster, Vec3 pos) {
        caster.removeEffect(EffectInit.INCARCEROUS_CAPTURA.get());

        var nbt = CAPTURED_ENTITIES.remove(caster.getUUID());
        if (nbt == null) return;

        var type = EntityType.byString(nbt.getString("entity")).orElse(null);
        if (type == null) return;

        var entity = (LivingEntity) type.create(caster.level);
        entity.load(nbt);
        entity.setPos(pos);
        entity.setHealth(entity.getMaxHealth());

        caster.level.addFreshEntity(entity);
        ScaleTypes.BASE.getScaleData(entity).setTargetScale(1);
    }


    public IncarcerousCapturaEffect() {
        super(MobEffectCategory.NEUTRAL, IncarcerousCapturaSpell.ID);
    }

    @Override
    public void tick(LivingEntity target, int amplifier) {
        var slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 3, 2, true, false, false);
        target.addEffect(slowness);
    }

    @Override
    public void onExpired(LivingEntity target, int amplifier) {
        super.onExpired(target, amplifier);
        releaseEntity(target, target.position());
    }
}
