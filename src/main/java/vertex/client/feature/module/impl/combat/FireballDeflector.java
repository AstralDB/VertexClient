

package vertex.client.feature.module.impl.combat;

import vertex.client.VertexMain;
import vertex.client.feature.config.BooleanSetting;
import vertex.client.feature.config.EnumSetting;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.render.Renderer;
import vertex.client.helper.util.Rotations;
import vertex.client.helper.util.Utils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

import java.awt.Color;
import java.util.Objects;

public class FireballDeflector extends Module {
    final EnumSetting<Mode> mode = this.config.create(new EnumSetting.Builder<>(Mode.DeflectSomewhere).name("Mode")
            .description("How to change the fireball's motion (ReflectBack = reflect back at shooter, DeflectSomewhere = idc get it away)")
            .get());
    final BooleanSetting checkVel = this.config.create(new BooleanSetting.Builder(false).name("Check velocity")
            .description("Checks if the fireball is actually approaching before hitting. Can get funky with a lot of them")
            .get());

    public FireballDeflector() {
        super("FireballDeflector", "Deflects (or reflects) fireballs in your hit range", ModuleType.COMBAT);
    }

    boolean isApproaching(Vec3d checkAgainst, Vec3d checkPos, Vec3d checkVel) {
        if (!this.checkVel.getValue()) {
            return true;
        }
        // return true if distance with next velocity to check pos is smaller than current distance -> the fireball is coming closer to us
        return checkAgainst.distanceTo(checkPos) > checkAgainst.distanceTo(checkPos.add(checkVel));
    }

    void hit(FireballEntity fe) {
        Entity owner = fe.getOwner();
        if (owner != null) {
            // we are the owner of this fireball = we shot it = dont hit it again
            if (owner.equals(VertexMain.client.player)) {
                return;
            }
            if (mode.getValue() == Mode.ReflectBack) {
                Vec2f pitchYaw = Rotations.getPitchYawFromOtherEntity(fe.getPos().add(0, fe.getHeight() / 2, 0),
                        owner.getPos().add(0, owner.getHeight() / 2, 0)
                );
                PlayerMoveC2SPacket p = new PlayerMoveC2SPacket.LookAndOnGround(pitchYaw.y, pitchYaw.x, VertexMain.client.player.isOnGround());
                Objects.requireNonNull(VertexMain.client.getNetworkHandler()).sendPacket(p);
            }
        }
        Objects.requireNonNull(VertexMain.client.interactionManager).attackEntity(VertexMain.client.player, fe);
    }

    boolean inHitRange(Entity attacker, Entity target) {
        return attacker.getCameraPosVec(1f)
                .distanceTo(target.getPos().add(0, target.getHeight() / 2, 0)) <= Objects.requireNonNull(VertexMain.client.interactionManager)
                .getReachDistance();
    }

    @Override
    public void onFastTick() {
        for (Entity entity : Objects.requireNonNull(VertexMain.client.world).getEntities()) {
            if (entity instanceof FireballEntity fe) {
                if (inHitRange(Objects.requireNonNull(VertexMain.client.player), fe) && isApproaching(VertexMain.client.player.getPos(),
                        fe.getPos(),
                        fe.getVelocity()
                )) {
                    hit(fe);
                }
            }
        }
    }

    @Override
    public void tick() {

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
        if (isDebuggerEnabled()) {
            for (Entity entity : Objects.requireNonNull(VertexMain.client.world).getEntities()) {
                if (entity instanceof FireballEntity fe) {
                    if (fe.getOwner() != null) {
                        Entity owner = fe.getOwner();
                        Renderer.R3D.renderLine(Utils.getInterpolatedEntityPosition(owner).add(0, owner.getHeight() / 2, 0),
                                Utils.getInterpolatedEntityPosition(fe).add(0, fe.getHeight() / 2, 0),
                                Color.MAGENTA,
                                matrices
                        );
                    }
                    if (inHitRange(Objects.requireNonNull(VertexMain.client.player), fe)) {
                        Renderer.R3D.renderLine(Utils.getInterpolatedEntityPosition(VertexMain.client.player)
                                        .add(0, VertexMain.client.player.getHeight() / 2, 0),
                                Utils.getInterpolatedEntityPosition(fe).add(0, fe.getHeight() / 2, 0),
                                Color.RED,
                                matrices
                        );
                    }
                }
            }
        }
    }

    @Override
    public void onHudRender() {

    }

    public enum Mode {
        ReflectBack, DeflectSomewhere
    }
}
