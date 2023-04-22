package network.something.somepotter.magic.spell;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.magic.spell.core.ProjectileOrSelfSpell;
import network.something.somepotter.util.SpellColor;

public class AscendioSpell extends ProjectileOrSelfSpell {
    public static final String ID = "ascendio";

    public AscendioSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 5;
    }


    @Override
    public SpellColor getColor() {
        return SpellColor.FORCE;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        execute(hitResult.getEntity());
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
    }

    @Override
    public void onCastSelf() {
        if (caster.getLookAngle().x < -0.5) {
            execute(caster);
        }
    }

    public static void execute(Entity target) {
        var motion = target.getDeltaMovement().add(0, 2, 0);
        target.setDeltaMovement(motion);
        target.fallDistance = 0;
        target.hurtMarked = true;
    }
}

