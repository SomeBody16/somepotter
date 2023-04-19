package network.something.somemagic.spell;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somemagic.util.SpellColor;

public class Spell {

    public final String name;
    public final LivingEntity caster;

    public Spell(String name, LivingEntity caster) {
        this.name = name;
        this.caster = caster;
    }

    public int getCooldownTicks() {
        return 60;
    }

    public void cast() {
        playSound();
        if (caster instanceof Player player) {
            // TODO: Cooldown
            // player.getCooldowns().addCooldown(ItemInit.WAND.get(), this.getCooldownTicks());
        }
    }

    public void playSound() {
        Player player = null;
        var soundSource = SoundSource.HOSTILE;

        if (caster instanceof Player) {
            player = (Player) caster;
            soundSource = SoundSource.PLAYERS;
        }

        caster.getLevel().playSound(player, caster.getX(), caster.getY(), caster.getZ(),
                getSound(), soundSource, 1.0F, 1.0F);
    }

    public SoundEvent getSound() {
        return SoundEvents.WITCH_THROW;
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

    private boolean isEntityInConeOfSight(Vec3 relativePos, Vec3 direction, Vec3 rotation, double cosAngle) {
        // Calculate the dot product between the relative position and the direction vectors
        double dot = relativePos.normalize().dot(direction);

        // Calculate the dot product between the relative position and the rotation vectors
        double dotRotation = relativePos.normalize().dot(rotation);

        // Calculate the cosine of the angle between the direction vector and the relative position
        double cos = dot / relativePos.length();

        // Calculate the cosine of the angle between the rotation vector and the relative position
        double cosRotation = dotRotation / relativePos.length();

        // Check if the entity is within the cone of sight
        return cos >= cosAngle && cosRotation >= cosAngle;
    }

    public SpellColor getColor() {
        return SpellColor.ESSENTIAL;
    }
}
