package network.something.somemagic.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somemagic.entity.SpellEntity;
import network.something.somemagic.magic.SpellEffectTick;
import network.something.somemagic.magic.effect.TempestSpellEffect;
import network.something.somemagic.magic.spell.core.ProjectileOrSelfSpell;
import network.something.somemagic.util.SpellColor;

public class TempestSpell extends ProjectileOrSelfSpell {
    public static final String ID = "tempest";

    public TempestSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 25;
    }

    public int getAreaRange() {
        return 16;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        AABB aof = hitResult.getEntity().getBoundingBox().inflate(getAreaRange());
        thunderstormTarget(aof);
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
        AABB aof = new AABB(hitResult.getBlockPos()).inflate(getAreaRange());
        thunderstormTarget(aof);
    }

    @Override
    public void onCastSelf() {
        AABB aof = caster.getBoundingBox().inflate(getAreaRange());
        thunderstormTarget(aof);
    }

    public void thunderstormTarget(AABB areaOfEffect) {
        var effect = new TempestSpellEffect(this, areaOfEffect);
        SpellEffectTick.addEffect(effect);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.ESSENTIAL;
    }

}

