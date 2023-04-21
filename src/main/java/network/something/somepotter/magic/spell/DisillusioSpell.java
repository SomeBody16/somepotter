package network.something.somepotter.magic.spell;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.magic.spell.core.ProjectileOrSelfSpell;
import network.something.somepotter.util.SpellColor;

public class DisillusioSpell extends ProjectileOrSelfSpell {
    public static final String ID = "disillusio";

    public DisillusioSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 10;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        if (hitResult.getEntity() instanceof LivingEntity livingEntity) {
            makeInvisible(livingEntity);
        }
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
    }

    @Override
    public void onCastSelf() {
        makeInvisible(caster);
    }

    public void makeInvisible(LivingEntity target) {
        var effect = new MobEffectInstance(MobEffects.INVISIBILITY, 20 * 60);
        target.addEffect(effect);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.ESSENTIAL;
    }

}

