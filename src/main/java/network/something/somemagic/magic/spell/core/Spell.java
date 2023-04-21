package network.something.somemagic.magic.spell.core;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somemagic.util.SpellColor;

public abstract class Spell {

    public final String name;
    public final LivingEntity caster;

    protected Spell(String name, LivingEntity caster) {
        this.name = name;
        this.caster = caster;
    }

    public abstract int getCooldownTicks();

    public abstract SpellColor getColor();

    public void cast() {
        playCastSound();
        if (caster instanceof Player player) {
            // TODO: Cooldown
            // player.getCooldowns().addCooldown(ItemInit.WAND.get(), this.getCooldownTicks());
        }
    }

    public void playSound(SoundEvent sound, Vec3 pos, SoundSource soundSource) {
        caster.level.playSound(
                caster instanceof Player player ? player : null,
                pos.x, pos.y, pos.z,
                sound, soundSource,
                1.0F, 10.F
        );
    }

    public void playSound(SoundEvent sound, BlockPos pos, SoundSource soundSource) {
        playSound(sound, new Vec3(pos.getX(), pos.getY(), pos.getZ()), soundSource);
    }

    public void playCastSound() {
        playSound(
                getCastSound(),
                caster.getEyePosition(),
                caster instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE
        );
    }

    public SoundEvent getCastSound() {
        return SoundEvents.FIRECHARGE_USE;
    }

    public double getCastRange() {
        return 64;
    }

    public boolean getCastHitFluids() {
        return false;
    }

    protected HitResult getCasterPOVHitResult() {
        return caster.pick(getCastRange(), 1.0F, getCastHitFluids());
    }
}
