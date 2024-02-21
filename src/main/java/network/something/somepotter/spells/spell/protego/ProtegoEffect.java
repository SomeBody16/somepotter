package network.something.somepotter.spells.spell.protego;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.effect.Effects;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.EffectInit;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spells.spell.SpellEffect;
import network.something.somepotter.spells.spell_type.curse.CurseType;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ProtegoEffect extends SpellEffect<ProtegoSpell> {
    public ProtegoEffect() {
        super(MobEffectCategory.BENEFICIAL, ProtegoSpell.ID);
    }

    @SubscribeEvent
    public static void onSpellHit(SpellHitEvent.Pre<?> event) {
        if (event.hitResult instanceof EntityHitResult hitResult
                && hitResult.getEntity() instanceof LivingEntity livingEntity
                && livingEntity.hasEffect(EffectInit.PROTEGO.get())) {

            var isCurse = event.spell.getType() instanceof CurseType;
            event.setCanceled(!isCurse);

            livingEntity.removeEffect(EffectInit.PROTEGO.get());
            var color = SpellInit.get(ProtegoSpell.ID).getColor();
            Effects.touch(livingEntity.level, livingEntity.getEyePosition(), color);
        }
    }
}
