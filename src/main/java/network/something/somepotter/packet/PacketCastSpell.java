package network.something.somepotter.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.ItemInit;
import network.something.somepotter.magic.Spells;
import network.something.somepotter.magic.spell.core.Spell;
import network.something.somepotter.magic.spell.core.UnknownSpell;

import java.util.function.Supplier;

import static net.minecraft.Util.NIL_UUID;

public class PacketCastSpell {

    private final String spellName;

    public PacketCastSpell(String spellName) {
        this.spellName = spellName;
    }

    public PacketCastSpell(FriendlyByteBuf buf) {
        this.spellName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(spellName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        var ctx = supplier.get();
        ctx.enqueueWork(() -> {
            // We are server side
            ServerPlayer caster = ctx.getSender();
            if (caster == null) {
                return;
            }
            SomePotter.LOGGER.info("{} is casting {}", caster.getDisplayName(), spellName);

            var spellId = spellName.toLowerCase().replaceAll(" ", "_");
            Spell spell = Spells.getSpell(spellId, caster);
            if (spell instanceof UnknownSpell) {
                var message = new TextComponent("Unknown spell: ");
                message.append(new TextComponent(spellName).withStyle(ChatFormatting.RED));
                caster.sendMessage(message, NIL_UUID);
                return;
            }

            if (caster.getUseItem().is(ItemInit.WAND.get())) {
                var message = new TextComponent("Casting: ");
                message.append(new TextComponent(spellName).withStyle(ChatFormatting.GREEN));
                caster.sendMessage(message, NIL_UUID);
                spell.cast();
            }
        });
        return true;
    }

}
