package network.something.somemagic.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somemagic.entity.SpellEntity;
import network.something.somemagic.magic.SpellEffectTick;
import network.something.somemagic.magic.effect.AccioSpellEffect;
import network.something.somemagic.magic.spell.core.TouchSpell;
import network.something.somemagic.util.SpellColor;

public class AccioSpell extends TouchSpell {
    public static final String ID = "accio";

    public AccioSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 15;
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.FORCE;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        super.onHitEntity(spellEntity, hitResult);

        var pullEffect = new AccioSpellEffect(this, hitResult.getEntity());
        SpellEffectTick.addEffect(pullEffect);
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
        super.onHitBlock(spellEntity, hitResult);

        var blockPos = hitResult.getBlockPos();
        AABB areaOfEffect = new AABB(blockPos).inflate(3.0);

        spellEntity.level.getEntities(caster, areaOfEffect).forEach(entity -> {
            var pullEffect = new AccioSpellEffect(this, entity);
            SpellEffectTick.addEffect(pullEffect);
        });
    }
}

