

package vertex.client.feature.module.impl.movement;

import vertex.client.VertexMain;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import net.minecraft.client.util.math.MatrixStack;

public class AirJump extends Module {

    public AirJump() {
        super("AirJump", "Allows you to jump mid air", ModuleType.MOVEMENT);
    }

    @Override
    public void tick() {
        if (VertexMain.client.player == null || VertexMain.client.getNetworkHandler() == null) {
            return;
        }
        if (VertexMain.client.options.jumpKey.isPressed()) {
            VertexMain.client.player.setOnGround(true);
            VertexMain.client.player.fallDistance = 0f;
        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public String getContext() {
        return null;
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {

    }

    @Override
    public void onHudRender() {

    }
}
