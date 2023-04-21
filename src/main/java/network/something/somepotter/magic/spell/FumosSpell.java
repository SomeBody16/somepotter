package network.something.somepotter.magic.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.entity.SpellEntity;
import network.something.somepotter.magic.spell.core.ProjectileOrSelfSpell;
import network.something.somepotter.util.ParticleUtil;
import network.something.somepotter.util.SpellColor;

public class FumosSpell extends ProjectileOrSelfSpell {
    public static final String ID = "fumos";

    public FumosSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 7;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        spawnSmokescreen(spellEntity.level, hitResult.getEntity().getEyePosition());
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
        spawnSmokescreen(spellEntity.level, hitResult.getLocation());
    }

    @Override
    public void onCastSelf() {
        spawnSmokescreen(caster.level, caster.getEyePosition());
    }

    public void spawnSmokescreen(Level level, Vec3 pos) {
        ParticleUtil.spawn(level, ParticleTypes.CAMPFIRE_COSY_SMOKE, pos, 4, 20);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.ESSENTIAL;
    }

}

