

package vertex.client.feature.command.impl;

import vertex.client.feature.command.argument.PlayerFromNameArgumentParser;
import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.coloring.StaticArgumentServer;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import vertex.client.helper.util.Utils;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Objects;

public class Invsee extends Command {

    public Invsee() {
        super("Invsee", "Shows you the inventory of another player", "invsee", "isee");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return StaticArgumentServer.serveFromStatic(index, new PossibleArgument(
                ArgumentType.STRING,
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
        validateArgumentsLength(args, 1, "Provide target username");
        PlayerEntity t = new PlayerFromNameArgumentParser(true).parse(args[0]);
        Utils.TickManager.runOnNextRender(() -> VertexMain.client.setScreen(new InventoryScreen(t)));
    }
}
