

package vertex.client.feature.command.impl;

import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.network.packet.c2s.play.UpdateCommandBlockC2SPacket;
import net.minecraft.util.math.Direction;

public class CheckCmd extends Command {
    public CheckCmd() {
        super("CheckCmd", "Check if command blocks are enabled", "checkCmd");
    }

    @Override
    public void onExecute(String[] args) {
        message("Checking command blocks");
        VertexMain.client.player.networkHandler.sendPacket(new UpdateCommandBlockC2SPacket(VertexMain.client.player.getBlockPos().offset(Direction.DOWN, 1),
                "/",
                CommandBlockBlockEntity.Type.AUTO,
                false,
                false,
                false
        ));
    }
}
