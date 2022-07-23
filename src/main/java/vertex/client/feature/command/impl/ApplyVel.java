

package vertex.client.feature.command.impl;

import vertex.client.feature.command.argument.StreamlineArgumentParser;
import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;

public class ApplyVel extends Command {

    public ApplyVel() {
        super("ApplyVel", "Apply velocity to your player", "velocity", "vel", "applyVel", "yeet");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return switch (index) {
            case 0 -> new PossibleArgument(ArgumentType.NUMBER, "(x velocity)");
            case 1 -> new PossibleArgument(ArgumentType.NUMBER, "(y velocity)");
            case 2 -> new PossibleArgument(ArgumentType.NUMBER, "(z velocity)");
            default -> super.getSuggestionsWithType(index, args);
        };
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 3, "Provide X, Y and Z velocity");

        StreamlineArgumentParser dap = new StreamlineArgumentParser(args);
        double vx = dap.consumeDouble();
        double vy = dap.consumeDouble();
        double vz = dap.consumeDouble();

        VertexMain.client.player.addVelocity(vx, vy, vz);
    }

    @Override
    public ExamplesEntry getExampleArguments() {
        return new ExamplesEntry("1 2 3", "0 2 0");
    }
}
