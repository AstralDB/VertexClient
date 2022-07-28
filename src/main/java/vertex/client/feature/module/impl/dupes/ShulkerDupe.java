

package vertex.client.feature.module.impl.dupes;

import io.netty.buffer.Unpooled;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import vertex.client.VertexMain;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.PacketEvent;
import vertex.client.helper.util.Utils;

public class ShulkerDupe extends Module {

    public ShulkerDupe() {
        super("Shulker Dupe", "Dupes with shulkers!", ModuleType.MISC);
        Events.registerEventHandler(EventType.PACKET_SEND, event1 -> {
            if (!this.isEnabled()) {
                return;
            }
            PacketEvent event = (PacketEvent) event1;
            if (VertexMain.client.player != null && event.getPacket() instanceof PlayerActionC2SPacket actionC2SPacket) {
                if (actionC2SPacket.getAction() == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
                    for (int i = 0; i < 27; i++) {
                        Utils.Inventory.moveStackToOther(0, i);
                    }
                }
            }
            this.disable();
        });
    }

    @Override
    public void tick() {
        if (VertexMain.client.player == null || VertexMain.client.world == null || VertexMain.client.interactionManager == null) return;
        HitResult hit = VertexMain.client.crosshairTarget;

        if (hit instanceof BlockHitResult blockHit) {
            if (VertexMain.client.world.getBlockState(blockHit.getBlockPos()).getBlock() instanceof ShulkerBoxBlock && (VertexMain.client.player.currentScreenHandler instanceof ShulkerBoxScreenHandler)) {
                VertexMain.client.interactionManager.updateBlockBreakingProgress(blockHit.getBlockPos(), Direction.DOWN);
            } else {
                Utils.Logging.warn("You need to have a shulker box screen open and look at a shulker box.");
                VertexMain.client.player.closeHandledScreen();
                this.disable();
            }
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
