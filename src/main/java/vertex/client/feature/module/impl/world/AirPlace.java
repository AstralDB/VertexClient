/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

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
import net.minecraft.item.BlockItem;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;

public class AirPlace extends Module {

    boolean enabled = false;

    public AirPlace() {
        super("AirPlace", "Places blocks in the air", ModuleType.MISC);
        Events.registerEventHandler(EventType.MOUSE_EVENT, event -> {
            if (!this.isEnabled()) {
                return;
            }
            if (enabled && ((MouseEvent) event).getButton() == 1 && ((MouseEvent) event).getAction() == 1) {
                if (VertexMain.client.currentScreen != null) {
                    return;
                }
                try {
                    if (!client.world.getBlockState(((BlockHitResult) VertexMain.client.crosshairTarget).getBlockPos()).isAir()) {
                        return;
                    }
                    VertexMain.client.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND,
                            (BlockHitResult) VertexMain.client.crosshairTarget,
                            Utils.increaseAndCloseUpdateManager(VertexMain.client.world)
                    ));
                    if ((client.player.getMainHandStack().getItem() instanceof BlockItem)) {
                        Renderer.R3D.renderFadingBlock(Renderer.Util.modify(Utils.getCurrentRGB(), -1, -1, -1, 255),
                                Renderer.Util.modify(Utils.getCurrentRGB(), -1, -1, -1, 100).darker(),
                                Vec3d.of(((BlockHitResult) VertexMain.client.crosshairTarget).getBlockPos()),
                                new Vec3d(1, 1, 1),
                                1000
                        );
                    }
                    VertexMain.client.player.swingHand(Hand.MAIN_HAND);
                    event.setCancelled(true);
                } catch (Exception ignored) {
                }
            }
        });
    }

    @Override
    public void tick() {

    }

    @Override
    public void enable() {
        enabled = true;
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
