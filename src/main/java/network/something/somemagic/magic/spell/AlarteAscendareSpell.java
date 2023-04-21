package network.something.somemagic.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somemagic.entity.SpellEntity;
import network.something.somemagic.magic.spell.core.ProjectileSpell;
import network.something.somemagic.util.SpellColor;

public class AlarteAscendareSpell extends ProjectileSpell {
    public static final String ID = "alarte_ascendare";

    public AlarteAscendareSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 5;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        hitResult.getEntity().setDeltaMovement(0, 2, 0);
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.FORCE;
    }

}

