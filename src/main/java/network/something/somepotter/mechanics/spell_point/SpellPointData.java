package network.something.somepotter.mechanics.spell_point;

import ca.lukegrahamlandry.lib.data.impl.PlayerDataWrapper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import network.something.somepotter.SomePotter;

public class SpellPointData {

    int points = 0;

    protected static PlayerDataWrapper<SpellPointData> DATA;

    public static int get(Player player) {
        return DATA.get(player).points;
    }

    public static void set(ServerPlayer player, int points) {
        DATA.get(player).points = points;
        DATA.setDirty(player);
    }

    public static void add(ServerPlayer player, int points) {
        DATA.get(player).points += points;
        DATA.setDirty(player);
    }

    public static void init() {
        DATA = new PlayerDataWrapper<>(SpellPointData.class)
                .dir(SomePotter.MOD_ID)
                .named("skill_points")
                .synced()
                .saved();
    }

}
