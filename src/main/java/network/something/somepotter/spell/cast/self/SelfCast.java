package network.something.somepotter.spell.cast.self;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.cast.AbstractCast;

public class SelfCast extends AbstractCast {

    @Override
    public String getId() {
        return "self";
    }

    @Override
    public void execute(LivingEntity caster, String spellId) {
        var hitResult = new EntityHitResult(caster);
        var event = new SpellHitEntityEvent(spellId, caster, hitResult);
        MinecraftForge.EVENT_BUS.post(event);
    }
}
