package network.something.somepotter.magic.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.magic.spell.core.ProjectileSpell;
import network.something.somepotter.util.SpellColor;

public class BombardaSpell extends ProjectileSpell {
    public static final String ID = "bombarda";

    public BombardaSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 8;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        Entity hitEntity = hitResult.getEntity();

        spellEntity.getLevel().explode(spellEntity,
                hitEntity.getX(), hitEntity.getY(), hitEntity.getZ(),
                4.0f, false, Explosion.BlockInteraction.DESTROY);
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
        BlockPos hitPos = hitResult.getBlockPos();
        spellEntity.getLevel().explode(spellEntity,
                hitPos.getX(), hitPos.getY(), hitPos.getZ(),
                4.0f, false, Explosion.BlockInteraction.DESTROY);

    }

    @Override
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }
}

