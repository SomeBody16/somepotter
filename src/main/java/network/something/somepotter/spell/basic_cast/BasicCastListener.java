package network.something.somepotter.spell.basic_cast;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somepotter.SomePotter;
import network.something.somepotter.cast.touch.TouchCast;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.SpellListener;

public class BasicCastListener extends SpellListener<BasicCastSpell> {

    @Override
    public void onSpellCast(SpellCastEvent.Post<BasicCastSpell> event) {
        var text = new TextComponent("BASIC CAST!");
        event.caster.sendMessage(text, event.caster.getUUID());
        SomePotter.LOGGER.info("BASIC CAST! {}", event.spell.getId());

        new TouchCast(event).execute();
    }

    @Override
    public void onSpellHitEntity(SpellHitEvent.Post<BasicCastSpell> event, EntityHitResult hitResult) {
        var entity = hitResult.getEntity();
        var damageSource = event.spell.getDamageSource(event.caster);
        var damage = event.abilityPower;
        entity.hurt(damageSource, damage);
    }
}
