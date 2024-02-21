package network.something.somepotter.mechanics.cooldown;

import ca.lukegrahamlandry.lib.config.Comment;
import ca.lukegrahamlandry.lib.config.ConfigWrapper;
import network.something.somepotter.SomePotter;

import java.util.function.Supplier;

public class CooldownClientConfig {

    protected static final Supplier<CooldownClientConfig> CONFIG =
            ConfigWrapper.client(CooldownClientConfig.class)
                    .dir(SomePotter.MOD_ID)
                    .named("cooldown");

    public static CooldownClientConfig get() {
        return CONFIG.get();
    }


    @Comment("The x position of the hud, -1 to auto")
    public int hudX = -1;

    @Comment("The y position of the hud, -1 to auto")
    public int hudY = -1;

    @Comment("The x offset of the hud")
    public int hudOffsetX = 0;

    @Comment("The y offset of the hud")
    public int hudOffsetY = 0;

    @Comment("Right to left mode")
    public boolean rightToLeft = false;

    @Comment("Top to bottom mode")
    public boolean topToBottom = false;

}
