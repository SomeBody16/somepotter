package network.something.somepotter.integration.patchouli;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.mechanics.spell_learn.SpellLearnClient;
import network.something.somepotter.spells.requirement.RequirementsClient;
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.function.UnaryOperator;

public class SpellRequirementsComponent implements ICustomComponent {

    public transient String spellId = "";
    public transient int x = 0, y = 0, pageNum = 0;

    protected transient Button learnButton;

    @Override
    public void build(int componentX, int componentY, int pageNum) {
        this.x = componentX;
        this.y = componentY;
        this.pageNum = pageNum;
    }

    @Override
    public void onDisplayed(IComponentRenderContext context) {
        RequirementsClient.requestFromServer(spellId);
        SpellLearnClient.requestFromServer();

        if (SpellLearnClient.isLearned(spellId)) return;

        learnButton = new ExtendedButton(x, y + 15, 110, 20,
                getLearnButtonText(), this::onLearnButtonPress);
        context.addWidget(learnButton, pageNum);
    }

    @Override
    public void render(PoseStack ms, IComponentRenderContext context, float ticks, int mouseX, int mouseY) {
        if (SpellLearnClient.isLearned(spellId)) {
            if (learnButton != null) learnButton.visible = false;
            return;
        }
        learnButton.visible = true;
        learnButton.active = RequirementsClient.isMet(spellId);

        var spell = SpellInit.get(spellId);
        var font = Minecraft.getInstance().font;

        var y = this.y + 24;
        for (var requirement : spell.getRequirements()) {
            var isMet = RequirementsClient.isMet(spellId, requirement);

            var prepend = new TextComponent(isMet ? "✓ " : "✗ ")
                    .setStyle(context.getFont())
                    .withStyle(ChatFormatting.BOLD)
                    .withStyle(isMet ? ChatFormatting.GREEN : ChatFormatting.RED);

            var text = ((MutableComponent) requirement.getText())
                    .setStyle(context.getFont())
                    .withStyle(ChatFormatting.BLACK)
                    .withStyle(ChatFormatting.ITALIC);

            var result = prepend.append(text);
            var color = RequirementsClient.isMet(spellId, requirement) ? 0x00FF00 : 0xFF0000;

            Minecraft.getInstance().font.draw(ms, result, x, y, color);
            y += font.lineHeight + 2;
        }
    }

    public void onLearnButtonPress(Button button) {
        SpellLearnClient.learn(spellId);
        Minecraft.getInstance().setScreen(null);
    }

    protected TranslatableComponent getLearnButtonText() {
        return new TranslatableComponent("spell_book.requirements.button");
    }

    @Override
    public void onVariablesAvailable(@NotNull UnaryOperator<IVariable> lookup) {
        spellId = lookup.apply(IVariable.wrap("#spell_id#")).asString();
    }
}
