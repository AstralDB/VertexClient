

package vertex.client.feature.command.argument;

import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class PlayerFromUuidArgumentParser implements ArgumentParser<PlayerEntity> {
    @Override
    public PlayerEntity parse(String argument) throws CommandException {
        if (VertexMain.client.world == null) {
            throw new CommandException("World is not loaded", "Join a world or server");
        }
        try {
            UUID u = UUID.fromString(argument);
            for (AbstractClientPlayerEntity player : VertexMain.client.world.getPlayers()) {
                if (player.getUuid().equals(u)) {
                    return player;
                }
            }
            throw new CommandException("Invalid argument \"" + argument + "\": Player not found", "Provide the uuid of an existing player");
        } catch (Exception e) {
            throw new CommandException("Invalid argument \"" + argument + "\": Expected an UUID", "Provide a valid UUID");
        }
    }
}
