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
import net.minecraft.world.GameMode;

import java.util.Arrays;

public class Gamemode extends Command {

    public Gamemode() {
        super("Gamemode", "Switch gamemodes client side", "gamemode", "gm", "gmode");
    }

    @Override
    public ExamplesEntry getExampleArguments() {
        return new ExamplesEntry("survival", "creative", "adventure");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return StaticArgumentServer.serveFromStatic(index,
                new PossibleArgument(ArgumentType.STRING, Arrays.stream(GameMode.values()).map(GameMode::getName).toList().toArray(String[]::new))
        );
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        if (VertexMain.client.interactionManager == null) {
            return;
        }
        validateArgumentsLength(args, 1, "Provide gamemode");
        GameMode gm = GameMode.byName(args[0], null);
        if (gm == null) {
            throw new CommandException("Invalid gamemode", "Specify a valid gamemode");
        }
        VertexMain.client.interactionManager.setGameMode(gm);
    }
}
