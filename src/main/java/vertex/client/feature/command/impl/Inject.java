

package vertex.client.feature.command.impl;

import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.coloring.StaticArgumentServer;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;

public class Inject extends Command {
    public Inject() {
        super("Inject", "Injects a chunk of nbt into the target item", "inject", "inj", "addNbt");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return StaticArgumentServer.serveFromStatic(index, new PossibleArgument(ArgumentType.STRING, "(nbt)"));
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 1, "Provide NBT");
        ItemStack is = VertexMain.client.player.getMainHandStack();

        String nString = String.join(" ", args);
        NbtCompound old = is.getOrCreateNbt();
        try {
            NbtCompound ncNew = StringNbtReader.parse(nString);
            old.copyFrom(ncNew);
            success("Item modified");
        } catch (Exception e) {
            error(e.getMessage());
        }
    }
}
