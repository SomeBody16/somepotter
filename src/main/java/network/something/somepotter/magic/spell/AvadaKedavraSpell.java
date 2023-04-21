package network.something.somepotter.magic.spell;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.magic.spell.core.ProjectileSpell;
import network.something.somepotter.util.DamageSourceUtil;
import network.something.somepotter.util.SpellColor;

public class AvadaKedavraSpell extends ProjectileSpell {
    public static final String ID = "avada_kedavra";

    public DamageSource getDamageSource() {
        return DamageSourceUtil.indirect("spell." + ID, caster);
    }

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
            livingEntity.hurt(getDamageSource(), 9999.0F);
            playSound(SoundEvents.WITHER_DEATH, hitResult.getLocation(), SoundSource.PLAYERS);
        }
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UNFORGIVEABLE;
    }
}

