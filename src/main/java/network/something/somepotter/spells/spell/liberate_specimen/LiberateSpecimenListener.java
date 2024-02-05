package network.something.somepotter.spells.spell.liberate_specimen;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell.incarcerous_captura.IncarcerousCapturaEffect;

public class LiberateSpecimenListener extends SpellListener<LiberateSpecimenSpell> {

    @Override
    public void onSpellHitBlock(SpellHitEvent.Post<LiberateSpecimenSpell> event, BlockHitResult hitResult) {
        var pos = Vec3.atBottomCenterOf(hitResult.getBlockPos().above());
        IncarcerousCapturaEffect.releaseEntity(event.caster, pos);
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<LiberateSpecimenSpell> event, EntityHitResult hitResult, Entity entity) {
        var blockHitResult = new BlockHitResult(entity.position(), entity.getDirection(), entity.blockPosition(), false);
        onSpellHitBlock(event, blockHitResult);
    }
}
