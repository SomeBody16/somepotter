package network.something.somepotter.cast.touch;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraftforge.common.MinecraftForge;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.spell.Spell;

public class TouchCast {

    public LivingEntity caster;
    public ServerLevel level;
    public Spell spell;

    public int abilityPower;
    public float areaOfEffect;
    public float inaccuracy = 1.0F;
    public float velocity = 32F;


    public TouchCast(SpellCastEvent<?> event) {
        this.caster = event.caster;
        this.level = event.level;
        this.spell = event.spell;
        this.abilityPower = event.abilityPower;
        this.areaOfEffect = event.areaOfEffect;
    }

    public void execute() {
        var entity = new Arrow(level, caster);
        entity.shootFromRotation(caster,
                caster.getXRot(), caster.getYRot(), 0.0F, velocity, inaccuracy);
        var result = ProjectileUtil.getHitResult(entity, (e) -> true);

        var spellHitEventPre = new SpellHitEvent.Pre<>();
        spellHitEventPre.spell = spell;
        spellHitEventPre.caster = caster;
        spellHitEventPre.level = level;
        spellHitEventPre.abilityPower = abilityPower;
        spellHitEventPre.areaOfEffect = areaOfEffect;
        spellHitEventPre.hitResult = result;

        var cancelled = MinecraftForge.EVENT_BUS.post(spellHitEventPre);
        if (cancelled) return;

        var spellHitEventPost = new SpellHitEvent.Post<>();
        spellHitEventPost.spell = spellHitEventPre.spell;
        spellHitEventPost.caster = spellHitEventPre.caster;
        spellHitEventPost.level = spellHitEventPre.level;
        spellHitEventPost.abilityPower = spellHitEventPre.abilityPower;
        spellHitEventPost.areaOfEffect = spellHitEventPre.areaOfEffect;
        spellHitEventPost.hitResult = spellHitEventPre.hitResult;
        MinecraftForge.EVENT_BUS.post(spellHitEventPost);

    }

}
