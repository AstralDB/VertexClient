

package vertex.client.helper.util;

import vertex.client.VertexMain;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class Packets {

    public static void sendServerSideLook(Vec3d target1) {
        double vec = 57.2957763671875;
        Vec3d target = target1.subtract(Objects.requireNonNull(VertexMain.client.player).getEyePos());
        double square = Math.sqrt(target.x * target.x + target.z * target.z);
        float pitch = MathHelper.wrapDegrees((float) (-(MathHelper.atan2(target.y, square) * vec)));
        float yaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(target.z, target.x) * vec) - 90.0F);
        PlayerMoveC2SPacket p = new PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, VertexMain.client.player.isOnGround());
        Objects.requireNonNull(VertexMain.client.getNetworkHandler()).sendPacket(p);
    }
}
