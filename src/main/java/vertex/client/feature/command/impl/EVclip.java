

package vertex.client.feature.command.impl;

import vertex.client.feature.command.argument.IntegerArgumentParser;
import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.coloring.StaticArgumentServer;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import net.minecraft.entity.Entity;

public class EVclip extends Command {
    public EVclip() {
        super("EVclip", "VClip with an entity", "evc", "evclip", "entityVclip");
    }

    @Override
    public ExamplesEntry getExampleArguments() {
        return new ExamplesEntry("1", "2", "69");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return StaticArgumentServer.serveFromStatic(index, new PossibleArgument(ArgumentType.NUMBER, "(amount)"));
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 1, "Provide height");

        if (!VertexMain.client.player.hasVehicle()) {
            error("You're not riding an entity");
            return;
        }

        int up = new IntegerArgumentParser().parse(args[0]);

        Entity vehicle = VertexMain.client.player.getVehicle();
        vehicle.updatePosition(vehicle.getX(), vehicle.getY() + up, vehicle.getZ());
        message("Teleported entity");
    }
}
