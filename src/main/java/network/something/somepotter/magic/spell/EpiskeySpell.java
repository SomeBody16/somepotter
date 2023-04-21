package network.something.somepotter.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.magic.spell.core.ProjectileOrSelfSpell;
import network.something.somepotter.util.SpellColor;

public class EpiskeySpell extends ProjectileOrSelfSpell {
    public static final String ID = "episkey";

    public EpiskeySpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 7;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        if (hitResult.getEntity() instanceof LivingEntity livingEntity) {
            heal(livingEntity);
        }
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
    }

    @Override
    public void onCastSelf() {
        heal(caster);
    }

    public void heal(LivingEntity target) {
        var healthRatio = target.getHealth() / target.getMaxHealth();
        if (healthRatio >= 0.7) {
            target.heal(target.getMaxHealth() - target.getHealth());
        }
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.ESSENTIAL;
    }

}

