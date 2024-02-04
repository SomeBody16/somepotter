package network.something.somepotter.spells.spell.engorgio;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import network.something.somepotter.spells.tickable.Tickable;
import network.something.somepotter.spells.tickable.Tickables;
import virtuoel.pehkui.api.ScaleTypes;

import static net.minecraft.Util.NIL_UUID;

public class RestoreSizeTickable extends Tickable {

    public static void clear(Entity target) {
        Tickables.removeIf(tickable -> {
            if (tickable instanceof RestoreSizeTickable restoreSizeTickable) {
                return restoreSizeTickable.target.getUUID().equals(target.getUUID());
            }
            return false;
        });
    }

    public static void refresh(Entity target, int duration) {
        clear(target);
        Tickables.add(new RestoreSizeTickable(target, duration));
    }


    protected final Entity target;

    public RestoreSizeTickable(Entity target, int duration) {
        super(duration);
        this.target = target;
    }

    @Override
    public void onExpired() {
        var scaleData = ScaleTypes.BASE.getScaleData(target);
        if (target instanceof ServerPlayer player) {
            var text = String.format("%.2f -> 1", scaleData.getScale());
            var message = new TextComponent(text).withStyle(ChatFormatting.GREEN);
            player.sendMessage(message, ChatType.GAME_INFO, NIL_UUID);
        }

        scaleData.setTargetScale(1);
    }
}
