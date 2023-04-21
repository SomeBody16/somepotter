package network.something.somepotter.magic.spell.core;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.SomePotter;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.util.SpellColor;

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
    public int getCooldownTicks() {
        return 0;
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.ESSENTIAL;
    }

    @Override
    public void cast() {
        SomePotter.LOGGER.warn("{} tried to cast unknown spell", caster.getDisplayName());
    }
}

