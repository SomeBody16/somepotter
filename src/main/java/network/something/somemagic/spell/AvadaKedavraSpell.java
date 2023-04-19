package network.something.somemagic.spell;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somemagic.entity.SpellEntity;
import network.something.somemagic.util.DamageSourceUtil;
import network.something.somemagic.util.SpellColor;

public class AvadaKedavraSpell extends ProjectileSpell {
    public static final String ID = "avada_kedavra";
    public static final DamageSource DAMAGE_SOURCE = DamageSourceUtil.create("spell." + ID)
            .setMagic()
            .setProjectile();

    public AvadaKedavraSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 15;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        if (hitResult.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.hurt(DAMAGE_SOURCE, 9999.0F);
        }
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UNFORGIVEABLE;
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.LIGHTNING_BOLT_IMPACT;
    }
}

