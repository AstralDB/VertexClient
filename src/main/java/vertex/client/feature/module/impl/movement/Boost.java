

package vertex.client.feature.module.impl.movement;

import vertex.client.VertexMain;
import vertex.client.feature.config.DoubleSetting;
import vertex.client.feature.config.EnumSetting;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.feature.module.NoNotificationDefault;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

@NoNotificationDefault
public class Boost extends Module {

    final DoubleSetting strength = this.config.create(new DoubleSetting.Builder(3).name("Strength")
            .description("How much to boost you with")
            .min(0.1)
            .max(10)
            .precision(1)
            .get());
    final EnumSetting<Mode> mode = this.config.create(new EnumSetting.Builder<>(Mode.Add).name("Mode").description("How to boost you").get());

    public Boost() {
        super("Boost", "Boosts you into the air", ModuleType.MOVEMENT);
    }

    @Override
    public void tick() {

    }

    @Override
    public void enable() {
        if (VertexMain.client.player == null || VertexMain.client.getNetworkHandler() == null) {
            return;
        }
        setEnabled(false);
        Vec3d newVelocity = VertexMain.client.player.getRotationVector().multiply(strength.getValue());
        if (this.mode.getValue() == Mode.Add) {
            VertexMain.client.player.addVelocity(newVelocity.x, newVelocity.y, newVelocity.z);
        } else {
            VertexMain.client.player.setVelocity(newVelocity);
        }
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

    public enum Mode {
        Add, Overwrite
    }
}
