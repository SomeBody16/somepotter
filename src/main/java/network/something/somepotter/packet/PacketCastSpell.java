package network.something.somepotter.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import network.something.somepotter.init.ItemInit;
import network.something.somepotter.spell.spells.Spells;
import network.something.somepotter.spell.spells.UnknownSpell;

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
            if (caster == null || !caster.getUseItem().is(ItemInit.WAND.get())) {
                return;
            }

            var spellId = spellName.toLowerCase().replaceAll(" ", "_");

            var spell = Spells.get(spellId);
            if (spell instanceof UnknownSpell) {
                var message = new TextComponent(spellName)
                        .withStyle(ChatFormatting.RED)
                        .withStyle(ChatFormatting.ITALIC);
                caster.sendMessage(message, NIL_UUID);
                return;
            }

            spell.cast(caster);
        });
        return true;
    }

}
