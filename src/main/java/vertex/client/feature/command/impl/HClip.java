

package vertex.client.feature.command.impl;

import vertex.client.feature.command.argument.DoubleArgumentParser;
import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.coloring.StaticArgumentServer;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class HClip extends Command {
    public HClip() {
        super("HClip", "Teleport horizontally", "hclip");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return StaticArgumentServer.serveFromStatic(index, new PossibleArgument(ArgumentType.NUMBER, "(amount)"));
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 1, "Provide distance");

        double brik = new DoubleArgumentParser().parse(args[0]);
        Vec3d forward = Vec3d.fromPolar(0, VertexMain.client.player.getYaw()).normalize();

        if (VertexMain.client.player.getAbilities().creativeMode) {
            VertexMain.client.player.updatePosition(VertexMain.client.player.getX() + forward.x * brik,
                    VertexMain.client.player.getY(),
                    VertexMain.client.player.getZ() + forward.z * brik
            );
        } else {
            clip(brik);
        }
    }

    private void clip(double blocks) {
        Vec3d pos = VertexMain.client.player.getPos();
        Vec3d forward = Vec3d.fromPolar(0, VertexMain.client.player.getYaw()).normalize();
        float oldy = VertexMain.client.player.getYaw();
        float oldp = VertexMain.client.player.getPitch();
        sendPosition(pos.x, pos.y + 9, pos.z);
        sendPosition(pos.x, pos.y + 18, pos.z);
        sendPosition(pos.x, pos.y + 27, pos.z);
        sendPosition(pos.x, pos.y + 36, pos.z);
        sendPosition(pos.x, pos.y + 45, pos.z);
        sendPosition(pos.x, pos.y + 54, pos.z);
        sendPosition(pos.x, pos.y + 63, pos.z);
        sendPosition(pos.x + forward.x * blocks, VertexMain.client.player.getY(), pos.z + forward.z * blocks);
        sendPosition(VertexMain.client.player.getX(), VertexMain.client.player.getY() - 9, VertexMain.client.player.getZ());
        sendPosition(VertexMain.client.player.getX(), VertexMain.client.player.getY() - 9, VertexMain.client.player.getZ());
        sendPosition(VertexMain.client.player.getX(), VertexMain.client.player.getY() - 9, VertexMain.client.player.getZ());
        sendPosition(VertexMain.client.player.getX(), VertexMain.client.player.getY() - 9, VertexMain.client.player.getZ());
        sendPosition(VertexMain.client.player.getX(), VertexMain.client.player.getY() - 9, VertexMain.client.player.getZ());
        sendPosition(VertexMain.client.player.getX(), VertexMain.client.player.getY() - 9, VertexMain.client.player.getZ());
        sendPosition(VertexMain.client.player.getX(), VertexMain.client.player.getY() - 8.9, VertexMain.client.player.getZ());
        sendPosition(VertexMain.client.player.getX(), VertexMain.client.player.getY(), VertexMain.client.player.getZ());
        VertexMain.client.player.setYaw(oldy);
        VertexMain.client.player.setPitch(oldp);
    }


    private void sendPosition(double x, double y, double z) {
        VertexMain.client.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, true));
    }
}
