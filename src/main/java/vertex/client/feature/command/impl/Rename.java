

package vertex.client.feature.command.impl;

import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.coloring.StaticArgumentServer;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import vertex.client.helper.util.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.text.Text;

import java.util.Objects;

public class Rename extends Command {

    public Rename() {
        super("Rename", "Renames an item (requires creative)", "rename", "rn", "name");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return StaticArgumentServer.serveFromStatic(index, new PossibleArgument(ArgumentType.STRING, "(new name)"));
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 1, "Provide new name");

        if (Objects.requireNonNull(VertexMain.client.player).getInventory().getMainHandStack().isEmpty()) {
            error("You're not holding anything");
            return;
        }
        ItemStack iStack = VertexMain.client.player.getInventory().getMainHandStack();
        iStack.setCustomName(Text.of("§r" + String.join(" ", args).replaceAll("&", "§")));
        if (!VertexMain.client.interactionManager.hasCreativeInventory()) {
            warn("You dont have creative mode; the item will only be renamed client side");
        } else {
            VertexMain.client.getNetworkHandler()
                    .sendPacket(new CreativeInventoryActionC2SPacket(Utils.Inventory.slotIndexToId(VertexMain.client.player.getInventory().selectedSlot),
                            iStack
                    ));
        }
    }
}
