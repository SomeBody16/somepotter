package network.something.somepotter.spell.api.cast;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;

public class SelfCast extends AbstractCast {

    @Override
    public void execute(LivingEntity caster, String spellId) {
        var hitResult = new EntityHitResult(caster);
        var event = new SpellHitEntityEvent(spellId, caster, hitResult);
        MinecraftForge.EVENT_BUS.post(event);
    }
}
