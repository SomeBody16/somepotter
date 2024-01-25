package network.something.somepotter.mixin;

import network.something.somepotter.spells.spell.protego_maxima.xaero.ClaimsHighlighter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.highlight.AbstractHighlighter;
import xaero.map.highlight.HighlighterRegistry;

import java.util.List;

@Mixin(HighlighterRegistry.class)
public class InjectHighlighterMixin {

    @Shadow
    private List<AbstractHighlighter> highlighters;

    @Inject(
            method = "end",
            at = @At("HEAD"),
            remap = false
    )
    private void end(CallbackInfo ci) {
        var highlighter = new ClaimsHighlighter();
        highlighters.add(highlighter);
    }

}
