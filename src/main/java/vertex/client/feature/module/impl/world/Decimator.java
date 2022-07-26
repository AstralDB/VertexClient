

package vertex.client.feature.module.impl.world;

import vertex.client.VertexMain;
import vertex.client.feature.config.DoubleSetting;
import vertex.client.feature.gui.notifications.Notification;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.render.Renderer;
import vertex.client.helper.util.Utils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.Color;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Decimator extends Module {
    final DoubleSetting radius = this.config.create(new DoubleSetting.Builder(100).precision(0)
            .name("Radius")
            .description("How much to erase on X and Z")
            .min(20)
            .max(500)
            .get());
    final DoubleSetting delay = this.config.create(new DoubleSetting.Builder(30).precision(0)
            .name("Delay")
            .description("How much delay to use while erasing")
            .min(0)
            .max(1000)
            .get());
    final AtomicBoolean cancel = new AtomicBoolean(false);
    Thread runner;
    Vec3d startPos = null;
    Vec3d latest = null;

    public Decimator() {
        super("Decimator", "Transforms a radius around you to void", ModuleType.WORLD);
    }

    @Override
    public void tick() {

    }

    void run() {
        for (double ox = -radius.getValue(); ox < radius.getValue(); ox += 4) {
            for (double oz = -radius.getValue(); oz < radius.getValue(); oz += 4) {
                if (cancel.get()) {
                    return;
                }
                Vec3d root = startPos.add(ox, 0, oz);
                BlockPos pp = new BlockPos(root);
                latest = Vec3d.of(pp);
                String chat = String.format(
                        "fill %d %d %d %d %d %d minecraft:air",
                        pp.getX() - 2,
                        Objects.requireNonNull(VertexMain.client.world).getBottomY(),
                        pp.getZ() - 2,
                        pp.getX() + 2,
                        VertexMain.client.world.getTopY() - 1,
                        pp.getZ() + 2
                );
                Objects.requireNonNull(VertexMain.client.player).sendCommand(chat);
                Utils.sleep((long) (delay.getValue() + 0));
            }
        }
        setEnabled(false);
    }

    @Override
    public void enable() {
        startPos = Objects.requireNonNull(VertexMain.client.player).getPos();
        cancel.set(false);
        runner = new Thread(this::run);
        runner.start();
    }

    @Override
    public void disable() {
        Notification.create(6000, "Voider", Notification.Type.INFO, "Waiting for cleanup...");
        cancel.set(true);
    }

    @Override
    public String getContext() {
        return null;
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {
        if (latest != null) {
            Renderer.R3D.renderFilled(
                    new Vec3d(latest.x - 2, Objects.requireNonNull(VertexMain.client.world).getBottomY(), latest.z - 2),
                    new Vec3d(5, 0.001, 5),
                    Utils.getCurrentRGB(),
                    matrices
            );
            Renderer.R3D.renderLine(
                    new Vec3d(latest.x + .5, VertexMain.client.world.getBottomY(), latest.z + .5),
                    new Vec3d(latest.x + .5, VertexMain.client.world.getTopY(), latest.z + .5),
                    Color.RED,
                    matrices
            );
        }
    }

    @Override
    public void onHudRender() {

    }
}
