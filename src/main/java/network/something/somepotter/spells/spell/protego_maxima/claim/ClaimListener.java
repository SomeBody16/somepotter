package network.something.somepotter.spells.spell.protego_maxima.claim;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.effect.Effects;
import network.something.somepotter.event.SpellCastEvent;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spells.spell.apparition.ApparitionSpell;
import network.something.somepotter.spells.spell.protego_maxima.ProtegoMaximaSpell;

import java.util.List;
import java.util.regex.Pattern;

import static net.minecraft.Util.NIL_UUID;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClaimListener {

    public static void denyAccess(ServerPlayer player, Vec3 pos) {
        var message = new TranslatableComponent("spell.protego_maxima.claim.deny_access")
                .withStyle(ChatFormatting.RED);
        player.sendMessage(message, ChatType.GAME_INFO, NIL_UUID);

        var color = SpellInit.get(ProtegoMaximaSpell.ID).getColor();

        var centerPos = Vec3.atCenterOf(new BlockPos(pos));
        Effects.touch(player.level, centerPos, color);
    }

    public static void denyAccess(ServerPlayer player, BlockPos pos) {
        denyAccess(player, new Vec3(pos.getX(), pos.getY(), pos.getZ()));
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getPlayer().getLevel() instanceof ServerLevel serverLevel
                && event.getPlayer() instanceof ServerPlayer serverPlayer
                && !serverPlayer.isCreative()
                && ClaimManager.exists(serverLevel, event.getPos())
                && !ClaimManager.hasAccess(serverLevel, serverPlayer, event.getPos())
        ) {
            if (isPublic(serverLevel, event.getPos())) {
                return;
            }

            if (isBlacklisted(serverLevel, event.getPos())) {
                event.setCanceled(true);
                denyAccess(serverPlayer, event.getPos());
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getWorld() instanceof ServerLevel serverLevel
                && event.getPlayer() instanceof ServerPlayer serverPlayer
                && !serverPlayer.isCreative()
                && !ClaimManager.hasAccess(serverLevel, serverPlayer, event.getPos())
        ) {
            event.setCanceled(true);
            denyAccess(serverPlayer, event.getPos());
        }
    }

    @SubscribeEvent
    public static void onExplode(ExplosionEvent.Start event) {
        var pos = event.getExplosion().getPosition();

        if (event.getWorld() instanceof ServerLevel serverLevel
                && ClaimManager.exists(serverLevel, pos)
        ) {
            event.setCanceled(true);

            var color = SpellInit.get(ProtegoMaximaSpell.ID).getColor();
            Effects.touch(serverLevel, event.getExplosion().getPosition(), color);

            var hitEvent = new SpellHitEvent.Post<>();
            hitEvent.level = serverLevel;
            hitEvent.hitResult = new BlockHitResult(
                    event.getExplosion().getPosition(),
                    Direction.DOWN,
                    new BlockPos(event.getExplosion().getPosition()),
                    false
            );
            new ProtegoMaximaSpell().playHitSound(hitEvent);
        }
    }

    @SubscribeEvent
    public static void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        var pos = new BlockPos(event.getX(), event.getY(), event.getZ());
        if (event.getEntity() instanceof Mob
                && event.getSpawnReason() == MobSpawnType.NATURAL
                && event.getWorld() instanceof ServerLevel serverLevel
                && ClaimManager.exists(serverLevel, pos)) {

            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void onPreSpellCast(SpellCastEvent.Pre<?> event) {
        if (event.is(ApparitionSpell.ID)
                && event.caster instanceof ServerPlayer serverPlayer
                && !ClaimManager.hasAccess(event.level, serverPlayer, event.caster.blockPosition())) {
            event.setCanceled(true);

            var color = SpellInit.get(ProtegoMaximaSpell.ID).getColor();
            Effects.touch(event.level, event.caster.getEyePosition(), color);

            var hitEvent = new SpellHitEvent.Post<>();
            hitEvent.level = event.level;
            hitEvent.hitResult = new BlockHitResult(
                    event.caster.getEyePosition(),
                    Direction.DOWN,
                    event.caster.blockPosition(),
                    false
            );
            event.spell.playHitSound(hitEvent);

            var message = new TranslatableComponent("spell.protego_maxima.claim.deny_access")
                    .withStyle(ChatFormatting.RED);
            serverPlayer.sendMessage(message, ChatType.GAME_INFO, NIL_UUID);
        }
    }

    public static boolean isBlacklisted(ServerLevel level, BlockPos pos) {
        var blockId = level.getBlockState(pos).getBlock().getRegistryName().toString();

        var config = ProtegoMaximaSpell.CONFIG.get();
        for (var regex : config.ppmBlacklist) {
            var pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            var matcher = pattern.matcher(blockId);
            if (matcher.find()) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPublic(ServerLevel level, BlockPos pos) {
        var toCheck = List.of(
                pos.above(),
                pos.below(),
                pos.north(),
                pos.south(),
                pos.east(),
                pos.west()
        );

        var config = ProtegoMaximaSpell.CONFIG.get();
        for (var checkPos : toCheck) {
            var checkId = level.getBlockState(checkPos).getBlock().getRegistryName().toString();
            for (var regex : config.publicBlocks) {
                var pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                var matcher = pattern.matcher(checkId);
                if (matcher.find()) {
                    return true;
                }
            }
        }

        return false;
    }


}
