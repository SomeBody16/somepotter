package network.something.somepotter.spell.protego_maxima;

import ca.lukegrahamlandry.lib.config.Comment;

import java.util.List;

public class ProtegoMaximaConfig {

    @Comment("RegExp, This blocks cannot be right clicked while in a claimed chunk")
    public List<String> ppmBlacklist = List.of(
            "storagenetwork:.+",
            "catalyst_infusion_table",
            "vault_diffuser",
            "tool_vise",
            "vault_recycler",
            "vault_artisan_station",
            "vault_forge",
            "magnet_modification_table",
            "vault_charm_controller",
            "vault_altar",
            "cryo_chamber",
            "button",
            "chest",
            "crate",
            "shulker",
            "cabinet",
            "computer",
            "monitor",
            "modem",
            "disk",
            "cabinet",
            "drawer",
            "router",
            "lever",
            "pot",
            "bar_panel",
            "rsgauges",
            "thermal",
            "ars_nouveau",
            "ars_omega"
    );

    @Comment("RegExp, Blocks that touch these blocks will be PPM clickable, including ppmBlacklist")
    public List<String> publicBlocks = List.of(
            "blockcarpentry:illusion_block"
    );

}
