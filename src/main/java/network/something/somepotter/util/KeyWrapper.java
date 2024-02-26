package network.something.somepotter.util;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeyWrapper {

    protected static final List<KeyWrapper> WRAPPERS = new ArrayList<>();

    protected final List<Consumer<Player>> onPressListeners = new ArrayList<>();
    protected final List<Consumer<Player>> onReleaseListeners = new ArrayList<>();
    protected final List<Consumer<Player>> onHeldTickListeners = new ArrayList<>();

    protected final KeyMapping key;
    protected boolean wasDown = false;

    public KeyWrapper(String modId, String name, String category, int keyCode) {
        var keyName = "key.%s.%s".formatted(modId, name);
        this.key = new KeyMapping(keyName, keyCode, category);
        ClientRegistry.registerKeyBinding(key);
        WRAPPERS.add(this);
    }

    public KeyWrapper(String modId, String name, String category) {
        this(modId, name, category, -1);
    }

    public KeyWrapper onPress(Consumer<Player> listener) {
        this.onPressListeners.add(listener);
        return this;
    }

    public KeyWrapper onRelease(Consumer<Player> listener) {
        this.onReleaseListeners.add(listener);
        return this;
    }

    public KeyWrapper onHeldTick(Consumer<Player> listener) {
        this.onHeldTickListeners.add(listener);
        return this;
    }

    public boolean isDown() {
        return key.isDown();
    }

    protected void clientTick(Player player) {
        if (key.isDown()) {
            if (!wasDown) {
                onPressListeners.forEach(l -> l.accept(player));
                wasDown = true;
            }
            onHeldTickListeners.forEach(l -> l.accept(player));
        } else {
            if (wasDown) {
                onReleaseListeners.forEach(l -> l.accept(player));
                wasDown = false;
            }
        }
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (event.phase != TickEvent.Phase.END || player == null) return;
        WRAPPERS.forEach(wrapper -> wrapper.clientTick(player));
    }

}
