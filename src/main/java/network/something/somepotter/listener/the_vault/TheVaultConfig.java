package network.something.somepotter.listener.the_vault;

import ca.lukegrahamlandry.lib.config.Comment;
import network.something.somepotter.spell.avada_kedavra.AvadaKedavraSpell;
import network.something.somepotter.util.ConfigUtil;

import java.util.Map;
import java.util.function.Supplier;

public class TheVaultConfig {

    protected static final Supplier<TheVaultConfig> CONFIG = ConfigUtil.wrap(TheVaultConfig.class, "the_vault");

    public static TheVaultConfig get() {
        return CONFIG.get();
    }

    @Comment("The amount of mana spell costs in percent of the max mana")
    public float manaCostDefault = 0.05F;

    @Comment("The amount of mana spell costs in percent of the max mana")
    public Map<String, Float> manaCosts = Map.of(
            AvadaKedavraSpell.ID, 1F
    );

}
