/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.command.impl;

import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.coloring.StaticArgumentServer;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import vertex.client.feature.command.argument.DoubleArgumentParser;
import net.minecraft.client.network.ClientPlayerEntity;

public class VClip extends Command {
    public VClip() {
        super("VClip", "Teleport vertically", "vclip");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return StaticArgumentServer.serveFromStatic(index, new PossibleArgument(ArgumentType.NUMBER, "(amount)"));
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 1, "Provide height");

        ClientPlayerEntity player = VertexMain.client.player;
        player.updatePosition(player.getX(), player.getY() + new DoubleArgumentParser().parse(args[0]), player.getZ());
    }
}
