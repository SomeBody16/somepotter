package network.something.somemagic.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somemagic.entity.SpellEntity;
import network.something.somemagic.magic.spell.core.ProjectileSpell;
import network.something.somemagic.magic.spell.util.PushUtil;
import network.something.somemagic.util.SpellColor;

public class DepulsoSpell extends ProjectileSpell {
    public static final String ID = "depulso";

    public DepulsoSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 7;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        PushUtil.fromCaster(caster, hitResult.getEntity());
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.FORCE;
    }

}

