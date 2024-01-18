package network.something.somepotter.mechanics.login_effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LoginEffectListener {

    public static void playSound(ServerLevel level, Vec3 pos, float pitch) {
        var sound = SoundEvents.ENCHANTMENT_TABLE_USE;
        level.playSound(
                null,
                pos.x, pos.y, pos.z,
                sound,
                SoundSource.PLAYERS,
                1.0F, pitch
        );
    }

    public static void playParticle(ServerLevel level, Vec3 pos) {
        level.sendParticles(
                ParticleTypes.ENCHANT,
                pos.x, pos.y, pos.z,
                80,
                1, 1, 1,
                0.01
        );
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer().getLevel() instanceof ServerLevel level) {

            var pos = event.getPlayer().getEyePosition();
            playParticle(level, pos);
            playSound(level, pos, 1.5F);
        }
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getPlayer().getLevel() instanceof ServerLevel level) {

            var pos = event.getPlayer().getEyePosition();
            playParticle(level, pos);
            playSound(level, pos, 0.1F);
        }
    }

}
