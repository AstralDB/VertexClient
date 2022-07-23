

package vertex.client.feature.module.impl.movement;

import vertex.client.VertexMain;
import vertex.client.feature.config.DoubleSetting;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ElytraFly extends Module {

    final DoubleSetting acceleration = this.config

            .create(new DoubleSetting.Builder(5).name("Acceleration").description("How fast you accelerate").min(1).max(10).get());

    final DoubleSetting verticalSpeed = this.config

            .create(new DoubleSetting.Builder(5).name("Vertical Speed").description("How fast you move vertically").min(1).max(10).get());

    public ElytraFly() {

        super("ElytraFly", "Allows you to fly with Elytra freely.", ModuleType.MOVEMENT);
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void tick() {

        ItemStack chestplate = VertexMain.client.player.getEquippedStack(EquipmentSlot.CHEST);
        if (chestplate.getItem() == Items.ELYTRA) {

            if (VertexMain.client.player.isFallFlying()) {

                float angle = (float) Math.toRadians(VertexMain.client.player.getYaw());
                Vec3d acceleration = new Vec3d(-MathHelper.sin(angle) * (this.acceleration.getValue() / 100),
                        0,
                        MathHelper.cos(angle) * (this.acceleration.getValue() / 100)
                );

                Vec3d vec = VertexMain.client.player.getVelocity();

                if (VertexMain.client.options.forwardKey.isPressed()) {
                    VertexMain.client.player.setVelocity(vec.add(acceleration));
                } else if (VertexMain.client.options.backKey.isPressed()) {
                    VertexMain.client.player.setVelocity(vec.subtract(acceleration));
                }

                Vec3d verticalSpeed = VertexMain.client.player.getVelocity();

                if (VertexMain.client.options.jumpKey.isPressed()) {
                    VertexMain.client.player.setVelocity(verticalSpeed.x, verticalSpeed.y + (this.verticalSpeed.getValue() / 40), verticalSpeed.z);
                } else if (VertexMain.client.options.sneakKey.isPressed()) {
                    VertexMain.client.player.setVelocity(verticalSpeed.x, verticalSpeed.y - (this.verticalSpeed.getValue() / 40), verticalSpeed.z);
                }
            }
        }
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
