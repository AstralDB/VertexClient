

package vertex.client.feature.command.impl;

import vertex.client.feature.command.argument.IntegerArgumentParser;
import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.coloring.StaticArgumentServer;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;

import java.util.Arrays;

public class MessageSpam extends Command {
    public MessageSpam() {
        super("MessageSpam", "Sends a large amount of messages quickly", "spam", "spamMessages");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return StaticArgumentServer.serveFromStatic(index,
                new PossibleArgument(ArgumentType.NUMBER, "(amount)"),
                new PossibleArgument(ArgumentType.STRING, "(message)")
        );
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 2, "Provide amount and message");
        int amount = new IntegerArgumentParser().parse(args[0]);
        for (int i = 0; i < amount; i++) {
            VertexMain.client.player.sendChatMessage(String.join("", Arrays.copyOfRange(args, 1, args.length)));
        }
    }
}
