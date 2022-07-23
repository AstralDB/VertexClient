/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.module.impl.movement;

import vertex.client.VertexMain;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Objects;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "Always sprints when you walk", ModuleType.MOVEMENT);
    }

    @Override
    public void tick() {
        if (VertexMain.client.player == null || VertexMain.client.getNetworkHandler() == null) {
            return;
        }
        if (VertexMain.client.options.forwardKey.isPressed() && !VertexMain.client.options.backKey.isPressed() && !VertexMain.client.player.isSneaking() && !VertexMain.client.player.horizontalCollision) {
            Objects.requireNonNull(client.player).setSprinting(true);
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
