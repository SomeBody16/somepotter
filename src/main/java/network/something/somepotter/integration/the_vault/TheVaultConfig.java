package network.something.somepotter.integration.the_vault;

import ca.lukegrahamlandry.lib.config.Comment;
import network.something.somepotter.spells.spell.alohomora.AlohomoraSpell;
import network.something.somepotter.spells.spell.avada_kedavra.AvadaKedavraSpell;
import network.something.somepotter.spells.spell.bombarda.BombardaSpell;
import network.something.somepotter.spells.spell.bombarda_maxima.BombardaMaximaSpell;
import network.something.somepotter.util.ConfigUtil;

import java.util.Map;
import java.util.function.Supplier;

public class TheVaultConfig {

    protected static final Supplier<TheVaultConfig> CONFIG = ConfigUtil.server(TheVaultConfig.class, "the_vault");

    public static TheVaultConfig get() {
        return CONFIG.get();
    }

    @Comment("The amount of mana spell costs in percent of the max mana")
    public float manaCostDefault = 0.05F;

    @Comment("The amount of mana spell costs in percent of the max mana")
    public Map<String, Float> manaCosts = Map.of(
            AvadaKedavraSpell.ID, 1F,
            AlohomoraSpell.ID, 0.9F,
            BombardaSpell.ID, 0.3F,
            BombardaMaximaSpell.ID, 0.5F
    );

}
