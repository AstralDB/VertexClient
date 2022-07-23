/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.module.impl.movement;

import vertex.client.VertexMain;
import vertex.client.feature.gui.notifications.Notification;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.util.Utils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.BoatEntity;

public class BoatPhase extends Module {

    public BoatPhase() {
        super("BoatPhase", "Allows you to go through blocks, when in a boat which sand is falling on", ModuleType.MOVEMENT);
    }

    @Override
    public void tick() {

    }

    @Override
    public void enable() {
        Utils.Logging.message("To use BoatPhase, go into a boat, move it all the way towards a wall and drop sand on the boat with you in it");
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
        if (VertexMain.client.player == null || VertexMain.client.getNetworkHandler() == null) {
            return;
        }
        if (!(VertexMain.client.player.getVehicle() instanceof BoatEntity)) {
            Notification.create(5000, "Boat phase", true, Notification.Type.INFO, "sir you need a boat");
            setEnabled(false);
            return;
        }
        VertexMain.client.player.getVehicle().noClip = true;
        VertexMain.client.player.getVehicle().setNoGravity(true);
        VertexMain.client.player.noClip = true;
    }

    @Override
    public void onHudRender() {

    }
}
