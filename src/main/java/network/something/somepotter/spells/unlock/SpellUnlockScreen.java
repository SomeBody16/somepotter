package network.something.somepotter.spells.unlock;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpellUnlockScreen extends Screen {
    public SpellUnlockScreen() {
        super(new TextComponent("Spell Unlock Screen"));
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float ticks) {
        super.render(stack, mouseX, mouseY, ticks);
    }
}
