

package vertex.client.feature.module.impl.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import vertex.client.VertexMain;
import vertex.client.feature.config.DoubleSetting;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.util.Utils;

import java.util.Dictionary;

public class HiddenPlayers extends Module {

    public HiddenPlayers() {
        super("HiddenPlayers", "Notifies you when your armor pieces are low!", ModuleType.UTIL);
    }

    @Override
    public void tick() { }

    @Override
    public void enable() {
        for (PlayerListEntry entry : VertexMain.client.getNetworkHandler().getPlayerList()) {
            Utils.Logging.message("Player Found: " + entry);
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
}
