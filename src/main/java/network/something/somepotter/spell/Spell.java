package network.something.somepotter.spell;

import com.github.cluelab.dollar.Point;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.util.AbilityPowerUtil;
import network.something.somepotter.util.ResourceUtil;
import network.something.somepotter.wand.GestureHandler;

public abstract class Spell {

    abstract public String getId();

    abstract public SpellListener getListener();

    public float getAreaOfEffect() {
        return 0;
    }


    public void cast(LivingEntity caster) {
        var spellCastEventPre = new SpellCastEvent.Pre<>();
        spellCastEventPre.caster = caster;
        spellCastEventPre.level = (ServerLevel) caster.getLevel();
        spellCastEventPre.spell = this;
        spellCastEventPre.abilityPower = AbilityPowerUtil.get(caster);
        spellCastEventPre.areaOfEffect = getAreaOfEffect();

        var cancelled = MinecraftForge.EVENT_BUS.post(spellCastEventPre);
        if (cancelled) return;

        var spellCastEventPost = new SpellCastEvent.Post<>();
        spellCastEventPost.caster = spellCastEventPre.caster;
        spellCastEventPost.level = spellCastEventPre.level;
        spellCastEventPost.spell = spellCastEventPre.spell;
        spellCastEventPost.abilityPower = spellCastEventPre.abilityPower;
        spellCastEventPost.areaOfEffect = spellCastEventPre.areaOfEffect;
        MinecraftForge.EVENT_BUS.post(spellCastEventPost);
    }


    public void register() {
        registerGesture();
        registerListener();
    }

    protected void registerListener() {
        var listener = getListener();

        MinecraftForge.EVENT_BUS.addListener((SpellCastEvent.Pre<?> event) -> {
            if (!event.is(getId())) return;
            listener.preSpellCast(event);
        });
        MinecraftForge.EVENT_BUS.addListener((SpellCastEvent.Post<?> event) -> {
            if (!event.is(getId())) return;
            listener.onSpellCast(event);
        });

        MinecraftForge.EVENT_BUS.addListener((SpellHitEvent.Pre<?> event) -> {
            if (!event.is(getId())) return;
            if (event.hitResult.getType() == HitResult.Type.BLOCK) {
                getListener().preSpellHitBlock(event, (BlockHitResult) event.hitResult);
            }
            if (event.hitResult.getType() == HitResult.Type.ENTITY) {
                var entity = ((EntityHitResult) event.hitResult).getEntity();
                if (entity instanceof ServerPlayer player) {
                    getListener().preSpellHitPlayer(event, (EntityHitResult) event.hitResult, player);
                } else {
                    getListener().preSpellHitEntity(event, (EntityHitResult) event.hitResult);
                }
            }
        });
        MinecraftForge.EVENT_BUS.addListener((SpellHitEvent.Post<?> event) -> {
            if (!event.is(getId())) return;
            if (event.hitResult.getType() == HitResult.Type.BLOCK) {
                getListener().onSpellHitBlock(event, (BlockHitResult) event.hitResult);
            }
            if (event.hitResult.getType() == HitResult.Type.ENTITY) {
                var entity = ((EntityHitResult) event.hitResult).getEntity();
                if (entity instanceof ServerPlayer player) {
                    getListener().onSpellHitPlayer(event, (EntityHitResult) event.hitResult, player);
                } else {
                    getListener().onSpellHitEntity(event, (EntityHitResult) event.hitResult);
                }
            }
        });
    }

    protected void registerGesture() {
        var gesture = ResourceUtil.loadJson("gestures/" + getId() + ".json");
        var name = gesture.get("name").getAsString();
        var points = gesture.getAsJsonArray("points");

        var pointArray = new Point[points.size()];
        for (int i = 0; i < points.size(); i++) {
            var point = points.get(i).getAsJsonObject();
            var x = point.get("x").getAsFloat();
            var y = point.get("y").getAsFloat();
            var strokeId = point.get("strokeId").getAsInt();
            pointArray[i] = new Point(x, y, strokeId);
        }

        GestureHandler.registerGesture(name, pointArray);
    }

    public DamageSource getDamageSource(LivingEntity caster) {
        DamageSource result;
        if (caster instanceof Player player) {
            result = DamageSource.playerAttack(player);
        } else {
            result = DamageSource.mobAttack(caster);
        }

        return result
                .setProjectile()
                .setMagic();
    }
}
