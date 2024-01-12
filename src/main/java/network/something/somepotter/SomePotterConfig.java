package network.something.somepotter;

import ca.lukegrahamlandry.lib.config.Comment;

import java.util.List;

public class SomePotterConfig {

    @Comment("The list of wands ids that can be used to cast spells")
    public List<String> wandItems = List.of("somepotter:wand", "the_vault:wand");

}
