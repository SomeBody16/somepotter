package network.something.somepotter.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.mechanics.spell_point.SpellPointData;
import network.something.somepotter.util.AbilityPowerUtil;

import static net.minecraft.Util.NIL_UUID;

public class SpellCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        var spellArg = Commands.argument("spell", SpellArgument.spell());

        spellArg = spellArg.then(
                Commands.literal("cast")
                        .executes(SpellCommand::cast)
        );
        spellArg = spellArg.then(
                Commands.literal("hit_entity")
                        .then(
                                Commands.argument("entity", EntityArgument.entity())
                                        .executes(SpellCommand::hit_entity)
                        )
        );
        spellArg = spellArg.then(
                Commands.literal("hit_block")
                        .then(
                                Commands.argument("pos", BlockPosArgument.blockPos())
                                        .executes(SpellCommand::hit_block)
                        )
        );

        var spell = Commands.literal("spell").then(spellArg);

        var spell_point = Commands.literal("spell_point")
                .then(
                        Commands.argument("player", EntityArgument.player())
                                .then(
                                        Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .then(
                                                        Commands.literal("set")
                                                                .executes(SpellCommand::setSpellPoints)
                                                )
                                                .then(
                                                        Commands.literal("add")
                                                                .executes(SpellCommand::addSpellPoints)
                                                )
                                                .then(
                                                        Commands.literal("get")
                                                                .executes(SpellCommand::getSpellPoints)
                                                )
                                )
                );

        dispatcher.register(
                Commands.literal("somepotter")
                        .then(spell)
                        .then(spell_point)
        );
    }

    protected static int getSpellPoints(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var player = EntityArgument.getPlayer(ctx, "player");
        var spellPoints = SpellPointData.get(player);

        var message = new TextComponent("Spell Points: " + spellPoints);
        ctx.getSource().sendSuccess(message, true);
        return 1;
    }

    protected static int addSpellPoints(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var player = EntityArgument.getPlayer(ctx, "player");
        var amount = IntegerArgumentType.getInteger(ctx, "amount");

        var oldAmount = SpellPointData.get(player);
        SpellPointData.add(player, amount);
        var newAmount = SpellPointData.get(player);

        var message = new TextComponent("Spell Points: " + oldAmount + " -> " + newAmount);
        ctx.getSource().sendSuccess(message, true);
        if (!ctx.getSource().getPlayerOrException().getUUID().equals(player.getUUID())) {
            player.sendMessage(message, NIL_UUID);
        }

        return 1;
    }

    protected static int setSpellPoints(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var player = EntityArgument.getPlayer(ctx, "player");
        var amount = IntegerArgumentType.getInteger(ctx, "amount");

        var oldAmount = SpellPointData.get(player);
        SpellPointData.set(player, amount);
        var newAmount = SpellPointData.get(player);

        var message = new TextComponent("Spell Points: " + oldAmount + " -> " + newAmount);
        ctx.getSource().sendSuccess(message, true);
        if (!ctx.getSource().getPlayerOrException().getUUID().equals(player.getUUID())) {
            player.sendMessage(message, NIL_UUID);
        }

        return 1;
    }

    protected static int cast(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var player = ctx.getSource().getPlayerOrException();
        var spell = SpellArgument.getSpell(ctx, "spell");

        spell.cast(player);
        return 1;
    }

    protected static int hit_entity(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var player = ctx.getSource().getPlayerOrException();
        var spell = SpellArgument.getSpell(ctx, "spell");
        var entity = EntityArgument.getEntity(ctx, "entity");

        var level = (ServerLevel) player.level;
        var abilityPower = AbilityPowerUtil.get(player);
        var hitResult = new EntityHitResult(entity);

        SpellHitEvent.publish(spell, player, level, abilityPower, spell.getAreaOfEffect(), hitResult);
        return 1;
    }

    protected static int hit_block(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var player = ctx.getSource().getPlayerOrException();
        var spell = SpellArgument.getSpell(ctx, "spell");
        var pos = BlockPosArgument.getLoadedBlockPos(ctx, "pos");

        var level = (ServerLevel) player.level;
        var abilityPower = AbilityPowerUtil.get(player);

        var location = new Vec3(pos.getX(), pos.getY(), pos.getZ());
        var hitResult = new BlockHitResult(location, Direction.UP, pos, true);

        SpellHitEvent.publish(spell, player, level, abilityPower, spell.getAreaOfEffect(), hitResult);
        return 1;
    }

}
