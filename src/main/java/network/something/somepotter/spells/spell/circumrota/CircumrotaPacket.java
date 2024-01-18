package network.something.somepotter.spells.spell.circumrota;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import net.minecraft.client.Minecraft;

public class CircumrotaPacket implements ClientSideHandler {
    @Override
    public void handle() {
        var x = (double) Minecraft.getInstance().player.getXRot();
        x *= (1 / 0.15D);
        x *= -2;

        var y = 180D;
        y *= (1 / 0.15D);

        Minecraft.getInstance().player.turn(y, x);
    }
}
