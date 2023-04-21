package network.something.somemagic.magic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somemagic.entity.SpellEntity;
import network.something.somemagic.magic.SpellEffectTick;
import network.something.somemagic.magic.effect.CrucioSpellEffect;
import network.something.somemagic.magic.spell.core.TouchSpell;
import network.something.somemagic.util.SpellColor;

public class CrucioSpell extends TouchSpell {
    public static final String ID = "crucio";

    public CrucioSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 20;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        super.onHitEntity(spellEntity, hitResult);

        var spellEffect = new CrucioSpellEffect(this, hitResult.getEntity());
        SpellEffectTick.addEffect(spellEffect);
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UNFORGIVEABLE;
    }
}

