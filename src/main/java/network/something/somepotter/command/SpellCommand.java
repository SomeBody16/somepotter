package network.something.somepotter.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.util.AbilityPowerUtil;

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

        dispatcher.register(
                Commands.literal("somepotter")
                        .then(spell)
        );
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
