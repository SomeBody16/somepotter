package network.something.somemagic.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somemagic.SomeMagic;
import network.something.somemagic.entity.SpellEntity;

public class UnknownProjectileSpell extends ProjectileSpell {
    public static final String ID = "unknown-projectile";

    public UnknownProjectileSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
    }

    @Override
    public void cast() {
        SomeMagic.LOGGER.warn("{} tried to cast unknown spell", caster.getDisplayName());
    }
}

