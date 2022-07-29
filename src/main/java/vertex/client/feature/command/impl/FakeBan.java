

package vertex.client.feature.command.impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.text.Text;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import vertex.client.feature.command.argument.PlayerFromNameArgumentParser;
import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.helper.util.Utils;

import java.util.Objects;

public class FakeBan extends Command {
    public FakeBan() {
        super("FakeBan", "Fake a ban reason", "fb", "fake");
    }

    @Override
    public ExamplesEntry getExampleArguments() {
        return new ExamplesEntry("Hacking", "Duping");
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 1, "Provide ban target's username");
        VertexMain.client.player.networkHandler.getConnection().disconnect(Text.of("§cYou are permanently banned from this server!§r\n§7Reason:§r§f " + args[0]));
    }
}
