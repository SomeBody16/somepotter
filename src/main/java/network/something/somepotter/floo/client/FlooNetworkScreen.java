package network.something.somepotter.floo.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import network.something.somepotter.SomePotter;
import network.something.somepotter.floo.network.FlooNode;
import network.something.somepotter.floo.packet.ChangeFlooNodeNamePacket;
import network.something.somepotter.floo.packet.TeleportToNodePacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class FlooNetworkScreen extends Screen {

    protected FlooNode origin;
    protected List<FlooNode> nodes;
    protected int page = 0;

    protected EditBox nameEdit;

    public FlooNetworkScreen(FlooNode origin, List<FlooNode> nodes) {
        super(new TextComponent("Floo Network"));
        this.origin = origin;
        this.nodes = nodes;
    }

    @Override
    protected void init() {
        initButtons();
        super.init();
    }

    protected void initButtons() {
        clearWidgets();

        var x = (width / 2) - 100;
        var y = 50;

        var perPage = (height / 2) / 22;

        for (var i = page * perPage; i < (page + 1) * perPage; i++) {
            if (i >= nodes.size()) break;
            var node = nodes.get(i);

            var display = new TextComponent(node.name);
            Button.OnPress handler = btn -> {
                onClose();
                DisableFlooNetworkScreen.disableForPosition(node.getPos());
                new TeleportToNodePacket(node).sendToServer();
            };
            var btn = new ExtendedButton(x, y, 200, 20, display, handler);
            addRenderableWidget(btn);
            y += 22;
        }

        y = 50 + (perPage * 22) + 10;
        Function<Integer, Button.OnPress> getChangePage = (i) -> (btn) -> {
            page += i;
            page = Math.max(page, 0);
            page = Math.min(page, nodes.size() / perPage);
            initButtons();
        };
        var prevDisplay = new TranslatableComponent("spectatorMenu.previous_page");
        var prevBtn = new ExtendedButton(x, y, 95, 20, prevDisplay, getChangePage.apply(-1));
        prevBtn.active = page > 0;
        addRenderableWidget(prevBtn);

        var nextDisplay = new TranslatableComponent("spectatorMenu.next_page");
        var nextBtn = new ExtendedButton(x + 105, y, 95, 20, nextDisplay, getChangePage.apply(1));
        nextBtn.active = page < nodes.size() / perPage;
        addRenderableWidget(nextBtn);


        y += 25;
        if (nameEdit == null) {
            var nameEditDisplay = new TranslatableComponent("floo_network.name_edit");
            nameEdit = new EditBox(font, x + 1, y, 200 - 2, 20, nameEditDisplay);
            nameEdit.setValue(origin.name);
        }
        addRenderableWidget(nameEdit);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        SomePotter.LOGGER.info("name: {} | origin: {}", nameEdit.getValue(), origin.name);
        if (!nameEdit.getValue().equals(origin.name)) {
            SomePotter.LOGGER.info("sending packet");
            new ChangeFlooNodeNamePacket(origin, nameEdit.getValue()).sendToServer();
        }
        DisableFlooNetworkScreen.disableForPosition(origin.getPos());
        super.onClose();
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float ticks) {
        fill(stack, 0, 0, width, height, 0x88000000);

        var display = new TranslatableComponent("floo_network.select_destination");
        drawCenteredString(stack, font, display, width / 2, 50 - font.lineHeight - 5, 0xFFFFFF);

        super.render(stack, mouseX, mouseY, ticks);
    }
}
