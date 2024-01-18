package network.something.somepotter.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TextComponent;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.accio.AccioSpell;
import network.something.somepotter.spells.spell.avada_kedavra.AvadaKedavraSpell;
import network.something.somepotter.spells.spell.basic_cast.BasicCastSpell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SpellArgument implements ArgumentType<Spell> {
    private static final Collection<String> EXAMPLES = Arrays.asList(
            BasicCastSpell.ID, AccioSpell.ID, AvadaKedavraSpell.ID
    );

    public static final DynamicCommandExceptionType ERROR_INVALID_VALUE = new DynamicCommandExceptionType((value) -> new TextComponent("Invalid spell: " + value));


    public static SpellArgument spell() {
        return new SpellArgument();
    }

    public static Spell getSpell(CommandContext<CommandSourceStack> pContext, String pName) {
        return pContext.getArgument(pName, Spell.class);
    }

    @Override
    public Spell parse(StringReader reader) throws CommandSyntaxException {
        var str = reader.readUnquotedString();

        if (SpellInit.has(str)) {
            return SpellInit.get(str);
        } else {
            throw ERROR_INVALID_VALUE.create(str);
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        List<String> spellIds = new ArrayList<>();
        for (var spell : SpellInit.all()) {
            spellIds.add(spell.getId());
        }

        return SharedSuggestionProvider.suggest(spellIds, builder);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
