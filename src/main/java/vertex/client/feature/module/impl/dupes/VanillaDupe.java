

package vertex.client.feature.module.impl.dupes;

import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import vertex.client.VertexMain;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.PacketEvent;
import vertex.client.helper.util.Utils;

public class VanillaDupe extends Module {

    public VanillaDupe() {
        super("Vanilla Dupe", "Dupes on Vanilla servers, patched on 1.14+", ModuleType.MISC);
    }

    @Override
    public void tick() {
    }

    @Override
    public void enable() {
        VertexMain.client.player.dropSelectedItem(true);
        VertexMain.client.player.networkHandler.getConnection().disconnect(Text.of("Duping currently held item..."));
        this.disable();
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
