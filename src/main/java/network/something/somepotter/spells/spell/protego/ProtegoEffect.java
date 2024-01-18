package network.something.somepotter.spells.spell.protego;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.EffectInit;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spells.cast.touch.TouchCast;
import network.something.somepotter.spells.spell_type.curse.CurseType;
import network.something.somepotter.util.AbilityPowerUtil;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ProtegoEffect extends MobEffect {
    public ProtegoEffect() {
        super(MobEffectCategory.BENEFICIAL, SpellInit.get(ProtegoSpell.ID).getColor().getRGB24());
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onSpellHit(SpellHitEvent.Pre<?> event) {
        if (event.hitResult instanceof EntityHitResult hitResult
                && hitResult.getEntity() instanceof LivingEntity livingEntity
                && livingEntity.hasEffect(EffectInit.PROTEGO.get())) {

            var casterPower = AbilityPowerUtil.get(event.caster);
            var targetPower = AbilityPowerUtil.get(livingEntity);

            if (casterPower < targetPower * 2) {
                event.setCanceled(true);
            }

            if (event.spell.getType() instanceof CurseType) {
                event.setCanceled(false);
            }

            livingEntity.removeEffect(EffectInit.PROTEGO.get());
            TouchCast.playParticles(
                    SpellInit.get(ProtegoSpell.ID).getParticle(),
                    (ServerLevel) livingEntity.level,
                    livingEntity.position()
            );
        }
    }

    @SubscribeEvent
    public static void entityHurt(LivingHurtEvent event) {
        if (event.getEntityLiving().hasEffect(EffectInit.PROTEGO.get())) {
            var damage = event.getAmount();
            var maxDamage = event.getEntityLiving().getMaxHealth() / 2;

            if (damage < maxDamage) {
                event.setCanceled(true);
            }

            event.getEntityLiving().removeEffect(EffectInit.PROTEGO.get());
            TouchCast.playParticles(
                    SpellInit.get(ProtegoSpell.ID).getParticle(),
                    (ServerLevel) event.getEntityLiving().level,
                    event.getEntityLiving().position()
            );
        }
    }
}
