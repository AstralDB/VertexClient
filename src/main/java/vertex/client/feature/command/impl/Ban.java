

package vertex.client.feature.command.impl;

import vertex.client.feature.command.argument.PlayerFromNameArgumentParser;
import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import vertex.client.helper.util.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.text.Text;

import java.util.Objects;

public class Ban extends Command {
    public Ban() {
        super("Ban", "Ban people from re-joining the server", "ban", "block");
    }


    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        if (index == 0) {
            return new PossibleArgument(ArgumentType.PLAYER,
                    Objects.requireNonNull(VertexMain.client.world)
                            .getPlayers()
                            .stream()
                            .map(abstractClientPlayerEntity -> abstractClientPlayerEntity.getGameProfile().getName())
                            .toList()
                            .toArray(String[]::new)
            );
        }
        return super.getSuggestionsWithType(index, args);
    }

    @Override
    public ExamplesEntry getExampleArguments() {
        return new ExamplesEntry("Notch", "Player123");
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 1, "Provide ban target's username");
        PlayerFromNameArgumentParser parser = new PlayerFromNameArgumentParser(true);
        PlayerEntity playerN = parser.parse(args[0]);
        int[] playerUuid = Utils.Players.decodeUUID(playerN.getUuid());

        ItemStack ban = new ItemStack(Items.ARMOR_STAND, 1);
        message("Created Ban Stand for " + playerN.getGameProfile().getName());
        try {
            ban.setNbt(StringNbtReader.parse("{EntityTag:{UUID:[I;" + playerUuid[0] + "," + playerUuid[1] + "," + playerUuid[2] + "," + playerUuid[3] + "],ArmorItems:[{},{},{},{id:\"minecraft:player_head\",Count:1b,tag:{SkullOwner:\"" + playerN.getGameProfile()
                    .getName() + "\"}}]}}"));
        } catch (Exception ignored) {
        }
        ban.setCustomName(Text.of(playerN.getGameProfile().getName()));
        VertexMain.client.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(36 + VertexMain.client.player.getInventory().selectedSlot,
                ban
        ));
    }
}
