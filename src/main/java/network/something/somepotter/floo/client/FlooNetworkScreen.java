package network.something.somepotter.floo.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import network.something.somepotter.floo.network.FlooNode;
import network.something.somepotter.floo.packet.ChangeFlooNodeNamePacket;
import network.something.somepotter.floo.packet.SortNodePacket;
import network.something.somepotter.floo.packet.TeleportToNodePacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class FlooNetworkScreen extends Screen {

    @Nullable
    protected FlooNode origin;
    protected List<FlooNode> nodes;
    protected int page = 0;

    protected EditBox nameEdit;

    public FlooNetworkScreen(@Nullable FlooNode origin, List<FlooNode> nodes) {
        super(new TextComponent("Floo Network"));
        this.origin = origin;
        this.nodes = nodes;
    }

    public void tryOpen() {
        var mc = Minecraft.getInstance();
        var shouldNotOpen = DisableFlooNetworkScreen.isDisabled();
        if (mc.screen == null && !shouldNotOpen) mc.setScreen(this);
    }

    public void updateNodes(List<FlooNode> nodes) {
        this.nodes = nodes;
        initButtons();
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

            // Teleport button
            var display = new TextComponent(node.name);
            Button.OnPress handler = btn -> {
                onClose();
                DisableFlooNetworkScreen.disableForPosition(node.getPos());
                new TeleportToNodePacket(node).sendToServer();
            };
            var btn = new ExtendedButton(x, y, 200, 20, display, handler);
            btn.active = origin == null || !origin.equals(node);
            addRenderableWidget(btn);

            // Sort buttons
            Button.OnPress upHandler = upBtn -> {
                var newNodes = nodes.subList(0, nodes.size());
                var prevNodeIndex = newNodes.indexOf(node);
                newNodes.remove(node);
                newNodes.add(prevNodeIndex - 1, node);
                updateNodes(newNodes);
                new SortNodePacket(newNodes).sendToServer();
            };
            Button.OnPress downHandler = upBtn -> {
                var newNodes = nodes.subList(0, nodes.size());
                var prevNodeIndex = newNodes.indexOf(node);
                newNodes.remove(node);
                newNodes.add(prevNodeIndex + 1, node);
                updateNodes(newNodes);
                new SortNodePacket(newNodes).sendToServer();
            };

            var upDisplay = new TextComponent("↑");
            var downDisplay = new TextComponent("↓");

            var upBtn = new ExtendedButton(x + 200, y, 10, 10, upDisplay, upHandler);
            upBtn.active = i > 0;
            addRenderableWidget(upBtn);

            var downBtn = new ExtendedButton(x + 200, y + 10, 10, 10, downDisplay, downHandler);
            downBtn.active = i < nodes.size() - 1;
            addRenderableWidget(downBtn);

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
            nameEdit.setValue(origin == null ? "" : origin.name);
        }
        addRenderableWidget(nameEdit);

        nameEdit.visible = origin != null && (origin.allowedPlayers.isEmpty() || origin.allowedPlayers.contains(
                Minecraft.getInstance().player.getName().getString()
        ));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        if (origin != null) {
            if (!nameEdit.getValue().equals(origin.name)) {
                new ChangeFlooNodeNamePacket(origin, nameEdit.getValue()).sendToServer();
            }
            DisableFlooNetworkScreen.disableForPosition(origin.getPos());
        }
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
