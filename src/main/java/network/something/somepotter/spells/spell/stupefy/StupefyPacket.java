package network.something.somepotter.spells.spell.stupefy;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import net.minecraft.client.Minecraft;

import java.util.Random;

public class StupefyPacket implements ClientSideHandler {

    @Override
    public void handle() {
        var x = (double) Minecraft.getInstance().player.getXRot();
        x *= (1 / 0.15D);
        x *= -2;
        x *= getRandomMultiplier() * 2;

        var y = 180D;
        y *= (1 / 0.15D);
        y *= getRandomMultiplier();

        Minecraft.getInstance().player.turn(y, x);
    }

    protected float getRandomMultiplier() {
        var random = new Random();
        return random.nextFloat(2) - 1F;
    }

}
