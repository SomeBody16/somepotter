package network.something.somepotter.spells.spell.incarcerous_captura;

import iskallia.vault.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import network.something.somepotter.SomePotter;
import network.something.somepotter.particle.ParticleEffects;
import network.something.somepotter.spells.tickable.Tickable;
import network.something.somepotter.spells.tickable.Tickables;
import network.something.somepotter.util.AbilityPowerUtil;

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

        SomePotter.LOGGER.info("{} trying to capture {} | duration: {}",
                caster.getDisplayName().getString(), target.getDisplayName().getString(), this.duration);
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

        ParticleEffects.incarcerousCaptura(caster.level, caster.getEyePosition(), target.getEyePosition(), getColor());
    }

    @Override
    public void onExpired() {
        if (isSomethingWrong()) {
            captured = false;
        }

        sendFinishMessage();
        if (!captured) return;

        var jar = new ItemStack(ModItems.ANIMAL_JAR);
        var nbt = jar.getOrCreateTag();
        nbt.putString("entity", EntityType.getKey(target.getType()).toString());
        nbt.putInt("count", 1);
        target.save(nbt);

        ParticleEffects.touch(caster.level, target.getEyePosition(), spell.getColor());
        target.remove(Entity.RemovalReason.KILLED);

        var holdingDuration = AbilityPowerUtil.scale(abilityPower, 20 * 5, 20 * 20);
        var holdingTickable = new HoldingProcessTickable(holdingDuration, caster, target);
        Tickables.add(holdingTickable);
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
        var progress = (float) duration / (float) howLongToCapture(target);
        var r = (int) (progress * 255);
        var g = (int) ((1 - progress) * 255);
        return new Color(r, g, 0);
    }
}
