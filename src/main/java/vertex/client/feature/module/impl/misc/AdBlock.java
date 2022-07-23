/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.module.impl.misc;

import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.PacketEvent;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;

public class AdBlock extends Module {
    long blocked = 0;

    public AdBlock() {
        super("AdBlock", "Blocks the /ad command on minehut from sending to you", ModuleType.MISC);
        Events.registerEventHandler(EventType.PACKET_RECEIVE, event -> {
            if (!this.isEnabled()) {
                return;
            }
            PacketEvent pe = (PacketEvent) event;
            if (pe.getPacket() instanceof GameMessageS2CPacket msg) {
                if (msg.content().getString().contains("[AD]")) {
                    event.setCancelled(true);
                    blocked++;
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
        return blocked + "";
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {

    }

    @Override
    public void onHudRender() {

    }
}
