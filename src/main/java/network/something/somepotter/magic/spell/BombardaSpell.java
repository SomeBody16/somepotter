package network.something.somepotter.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.magic.spell.core.ProjectileSpell;
import network.something.somepotter.util.SpellColor;

public class BombardaSpell extends ProjectileSpell {
    public static final String ID = "bombarda";

    public BombardaSpell(LivingEntity caster) {
        super(ID, caster);
    }

    public BombardaSpell(String Id, LivingEntity caster) {
        super(Id, caster);
    }


    @Override
    public int getCooldownTicks() {
        return 20 * 8;
    }

    public float getExplosionRadius() {
        return 4.0f;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        execute(spellEntity.level, hitResult.getEntity().getEyePosition(), getExplosionRadius());
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
        execute(spellEntity.level, hitResult.getLocation(), getExplosionRadius());
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.DAMAGE;
    }

    public static void execute(Level level, Vec3 pos, float range) {
        level.explode(
                null,
                pos.x, pos.y, pos.z,
                range, false, Explosion.BlockInteraction.DESTROY
        );
    }
}

