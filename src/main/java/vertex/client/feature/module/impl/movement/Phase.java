

package vertex.client.feature.module.impl.movement;

import vertex.client.VertexMain;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.PacketEvent;
import vertex.client.helper.event.events.PlayerNoClipQueryEvent;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Box;

import java.util.Objects;

public class Phase extends Module {

    public Phase() {
        super("Phase", "Go through walls when flying (works best with creative)", ModuleType.MOVEMENT);
        Events.registerEventHandler(EventType.PACKET_SEND, event -> {
            if (!this.isEnabled() || VertexMain.client.player == null || !VertexMain.client.player.getAbilities().flying) {
                return;
            }
            PacketEvent pe = (PacketEvent) event;
            Box p = VertexMain.client.player.getBoundingBox(VertexMain.client.player.getPose()).offset(0, 0.27, 0).expand(0.25);
            if (p.getYLength() < 2) {
                p = p.expand(0, 1, 0);
            }
            p = p.offset(VertexMain.client.player.getPos());
            if (pe.getPacket() instanceof PlayerMoveC2SPacket && !Objects.requireNonNull(VertexMain.client.world).isSpaceEmpty(VertexMain.client.player, p)) {
                event.setCancelled(true);
            }
        });
        Events.registerEventHandler(EventType.NOCLIP_QUERY, event -> {
            if (!getNoClipState(((PlayerNoClipQueryEvent) event).getPlayer())) {
                return;
            }
            ((PlayerNoClipQueryEvent) event).setNoClipState(PlayerNoClipQueryEvent.NoClipState.ACTIVE);
        });
    }

    @Override
    public void tick() {
    }

    public boolean getNoClipState(PlayerEntity pe) {
        return this.isEnabled() && pe.getAbilities().flying;
    }

    @Override
    public void enable() {
        Objects.requireNonNull(VertexMain.client.player).setPose(EntityPose.STANDING);
        VertexMain.client.player.setOnGround(false);
        VertexMain.client.player.fallDistance = 0;
        VertexMain.client.player.setVelocity(0, 0, 0);
    }

    @Override
    public void disable() {

    }

    @Override
    public String getContext() {
        return getNoClipState(VertexMain.client.player) ? "Active" : null;
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {
        if (Objects.requireNonNull(VertexMain.client.player).getAbilities().flying) {
            VertexMain.client.player.setPose(EntityPose.STANDING);
            VertexMain.client.player.setOnGround(false);
            VertexMain.client.player.fallDistance = 0;
        }
    }

    @Override
    public void onHudRender() {

    }
}
