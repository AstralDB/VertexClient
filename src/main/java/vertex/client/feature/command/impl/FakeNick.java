

package vertex.client.feature.command.impl;

import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.coloring.StaticArgumentServer;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import vertex.client.mixin.IGameProfileMixin;
import net.minecraft.client.network.AbstractClientPlayerEntity;

import java.util.Arrays;
import java.util.Objects;

public class FakeNick extends Command {
    public FakeNick() {
        super("FakeNick", "Fakes an entity name for a certain player", "fakenick", "fn");
    }

    @Override
    public ExamplesEntry getExampleArguments() {
        return new ExamplesEntry("Airpipe Coffee developer", "Notch Herobrine");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return StaticArgumentServer.serveFromStatic(index, new PossibleArgument(
                ArgumentType.PLAYER,
                () -> Objects.requireNonNull(VertexMain.client.world)
                        .getPlayers()
                        .stream()
                        .map(abstractClientPlayerEntity -> abstractClientPlayerEntity.getGameProfile().getName())
                        .toList()
                        .toArray(String[]::new)
        ), new PossibleArgument(ArgumentType.STRING, "Adolf", "Fred", "Mark"));
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 2, "Player name and new name is required");
        String pname = args[0];
        String newName = String.join("_", Arrays.copyOfRange(args, 1, args.length)).replaceAll("&", "§");
        for (AbstractClientPlayerEntity player : VertexMain.client.world.getPlayers()) {
            if (player.getGameProfile().getName().equals(pname)) {
                success("Renamed " + player.getGameProfile().getName());
                ((IGameProfileMixin) player.getGameProfile()).coffee_setName(newName);
                return;
            }
        }
        error("No players called \"" + pname + "\" found");
    }
}
