package network.something.somemagic.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somemagic.entity.SpellEntity;
import network.something.somemagic.magic.spell.core.ProjectileSpell;
import network.something.somemagic.magic.spell.util.PushUtil;
import network.something.somemagic.util.SpellColor;

public class StupefySpell extends ProjectileSpell {
    public static final String ID = "stupefy";

    public StupefySpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 5;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        var hitEntity = hitResult.getEntity();
        PushUtil.fromCaster(caster, hitEntity);

        float randomXRot = hitEntity.level.random.nextFloat() * 360.0F;
        float randomYRot = hitEntity.level.random.nextFloat() * 360.0F;

        hitEntity.setXRot(randomXRot);
        hitEntity.setYRot(randomYRot);
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.ESSENTIAL;
    }

}

