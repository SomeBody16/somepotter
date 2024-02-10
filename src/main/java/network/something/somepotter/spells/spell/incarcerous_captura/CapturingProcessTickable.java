package network.something.somepotter.spells.spell.incarcerous_captura;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import network.something.somepotter.effect.Effects;
import network.something.somepotter.spells.tickable.Tickable;
import network.something.somepotter.util.AbilityPowerUtil;
import virtuoel.pehkui.api.ScaleTypes;

import java.awt.*;

public class CapturingProcessTickable extends Tickable {
    public static int howLongToCapture(LivingEntity target) {
        var maxHealth = target.getMaxHealth();
        return (int) (maxHealth * 20 * 0.9F);
    }

    protected IncarcerousCapturaSpell spell;
    protected int abilityPower;
    protected ServerPlayer caster;
    protected LivingEntity target;

    protected boolean captured = true;

    public CapturingProcessTickable(IncarcerousCapturaSpell spell, int abilityPower, ServerPlayer caster, LivingEntity target) {
        super(howLongToCapture(target));
        this.spell = spell;
        this.abilityPower = abilityPower;
        this.caster = caster;
        this.target = target;
    }

    @Override
    public void tick() {
        super.tick();

        if (isSomethingWrong()) {
            duration = 0;
            captured = false;
            return;
        }

        if (duration % 20 == 0) {
            var damageSource = spell.getDamageSource(caster);
            target.hurt(damageSource, 1);
        }

        var targetScale = Mth.lerp(getProgress(), 0.1F, 1);
        ScaleTypes.BASE.getScaleData(target).setScale(targetScale);

        Effects.incarcerousCaptura(caster.level, caster.getEyePosition(), target.getEyePosition(), getColor());
    }

    @Override
    public void onExpired() {
        if (isSomethingWrong()) {
            captured = false;
            if (target.isAlive()) {
                ScaleTypes.BASE.getScaleData(target).setTargetScale(1);
            }
        }

        sendFinishMessage();
        Effects.touch(caster.level, target.getEyePosition(), spell.getColor());
        if (!captured) return;

        var holdingDuration = AbilityPowerUtil.scale(abilityPower, 20 * 30, 20 * 60 * 10);
        IncarcerousCapturaEffect.captureEntity(caster, target, holdingDuration);
    }

    protected boolean isSomethingWrong() {
        return !target.isAlive()
                || caster.distanceTo(target) > 6
                || !caster.hasLineOfSight(target);
    }

    protected void sendFinishMessage() {
        var msg = new TextComponent(caster.getDisplayName().getString());
        if (captured) {
            msg.append(new TextComponent(" captured ").withStyle(ChatFormatting.GREEN));
        } else {
            msg.append(new TextComponent(" failed to capture ").withStyle(ChatFormatting.RED));
        }
        msg.append(target.getDisplayName().copy().withStyle(ChatFormatting.GOLD));
        caster.server.getPlayerList().broadcastMessage(msg, ChatType.CHAT, caster.getUUID());
    }

    protected Color getColor() {
        var progress = getProgress();
        var r = (int) (progress * 255);
        var g = (int) ((1 - progress) * 255);
        return new Color(r, g, 0);
    }

    protected float getProgress() {
        return (float) duration / (float) howLongToCapture(target);
    }
}
