

package vertex.client.feature.module.impl.world;

import vertex.client.VertexMain;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.MouseEvent;
import vertex.client.helper.render.Renderer;
import vertex.client.helper.util.Utils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.Color;
import java.util.Objects;

public class AnyPlacer extends Module {
    public AnyPlacer() {
        super("AnyPlacer", "Places spawn eggs with infinite reach (requires creative)", ModuleType.WORLD);
        Events.registerEventHandler(EventType.MOUSE_EVENT, event -> {
            if (!this.isEnabled()) {
                return;
            }
            if (VertexMain.client.player == null || VertexMain.client.world == null) {
                return;
            }
            if (VertexMain.client.currentScreen != null) {
                return;
            }
            //            PacketEvent pe = (PacketEvent) event;
            MouseEvent me = (MouseEvent) event;
            if ((me.getAction() == 1 || me.getAction() == 2) && me.getButton() == 1) {
                ItemStack sex = VertexMain.client.player.getMainHandStack();
                if (sex.getItem() instanceof SpawnEggItem) {
                    event.setCancelled(true);
                    HitResult hr = VertexMain.client.player.raycast(500, 0, true);
                    Vec3d spawnPos = hr.getPos();
                    NbtCompound entityTag = sex.getOrCreateSubNbt("EntityTag");
                    NbtList nl = new NbtList();
                    nl.add(NbtDouble.of(spawnPos.x));
                    nl.add(NbtDouble.of(spawnPos.y));
                    nl.add(NbtDouble.of(spawnPos.z));
                    entityTag.put("Pos", nl);
                    CreativeInventoryActionC2SPacket a = new CreativeInventoryActionC2SPacket(Utils.Inventory.slotIndexToId(VertexMain.client.player.getInventory().selectedSlot),
                            sex
                    );
                    Objects.requireNonNull(VertexMain.client.getNetworkHandler()).sendPacket(a);
                    BlockHitResult bhr = new BlockHitResult(VertexMain.client.player.getPos(),
                            Direction.DOWN,
                            new BlockPos(VertexMain.client.player.getPos()),
                            false
                    );
                    PlayerInteractBlockC2SPacket ib = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND,
                            bhr,
                            Utils.increaseAndCloseUpdateManager(VertexMain.client.world)
                    );
                    VertexMain.client.getNetworkHandler().sendPacket(ib);
                }
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
        if (isDebuggerEnabled()) {
            HitResult hr = Objects.requireNonNull(VertexMain.client.player).raycast(500, 0, true);
            Vec3d spawnPos = hr.getPos();
            Renderer.R3D.renderFilled(spawnPos.subtract(.3, 0, .3), new Vec3d(.6, 0.001, .6), Color.WHITE, matrices);
        }
    }

    @Override
    public void onHudRender() {

    }
}
