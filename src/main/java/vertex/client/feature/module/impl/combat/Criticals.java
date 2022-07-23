

package vertex.client.feature.module.impl.combat;

import vertex.client.feature.module.impl.movement.NoFall;
import vertex.client.VertexMain;
import vertex.client.feature.config.EnumSetting;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleRegistry;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.PacketEvent;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class Criticals extends Module {

    final EnumSetting<Mode> mode = this.config.create(new EnumSetting.Builder<>(Mode.Packet).name("Mode").description("How to deal crits").get());

    public Criticals() {
        super("Criticals", "Makes you deal a perfect 10/10 crit every time", ModuleType.COMBAT);
        Events.registerEventHandler(EventType.PACKET_SEND, event1 -> {
            PacketEvent event = (PacketEvent) event1;
            if (VertexMain.client.player == null || VertexMain.client.getNetworkHandler() == null) {
                return;
            }
            if (event.getPacket() instanceof PlayerInteractEntityC2SPacket && this.isEnabled()) {
                Vec3d ppos = VertexMain.client.player.getPos();
                ModuleRegistry.getByClass(NoFall.class).enabled = false; // disable nofall modifying packets when we send these
                switch (mode.getValue()) {
                    case Packet -> {
                        PlayerMoveC2SPacket.PositionAndOnGround p1 = new PlayerMoveC2SPacket.PositionAndOnGround(ppos.x, ppos.y + 0.2, ppos.z, true);
                        PlayerMoveC2SPacket.PositionAndOnGround p2 = new PlayerMoveC2SPacket.PositionAndOnGround(ppos.x, ppos.y, ppos.z, false);
                        PlayerMoveC2SPacket.PositionAndOnGround p3 = new PlayerMoveC2SPacket.PositionAndOnGround(ppos.x, ppos.y + 0.000011, ppos.z, false);
                        PlayerMoveC2SPacket.PositionAndOnGround p4 = new PlayerMoveC2SPacket.PositionAndOnGround(ppos.x, ppos.y, ppos.z, false);
                        VertexMain.client.getNetworkHandler().sendPacket(p1);
                        VertexMain.client.getNetworkHandler().sendPacket(p2);
                        VertexMain.client.getNetworkHandler().sendPacket(p3);
                        VertexMain.client.getNetworkHandler().sendPacket(p4);
                    }
                    case TpHop -> {
                        PlayerMoveC2SPacket.PositionAndOnGround p5 = new PlayerMoveC2SPacket.PositionAndOnGround(ppos.x, ppos.y + 0.02, ppos.z, false);
                        PlayerMoveC2SPacket.PositionAndOnGround p6 = new PlayerMoveC2SPacket.PositionAndOnGround(ppos.x, ppos.y + 0.01, ppos.z, false);
                        VertexMain.client.getNetworkHandler().sendPacket(p5);
                        VertexMain.client.getNetworkHandler().sendPacket(p6);
                    }
                }
                ModuleRegistry.getByClass(NoFall.class).enabled = true; // re-enable nofall
            }
        });

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

    }

    @Override
    public void onHudRender() {

    }

    public enum Mode {
        Packet, TpHop
    }
}
