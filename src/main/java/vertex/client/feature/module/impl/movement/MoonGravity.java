/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.module.impl.movement;

import vertex.client.VertexMain;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import net.minecraft.client.util.math.MatrixStack;

public class MoonGravity extends Module {

    public MoonGravity() {
        super("MoonGravity", "Imitates gravity on the moon", ModuleType.MOVEMENT);
    }

    @Override
    public void tick() {
        if (VertexMain.client.player == null || VertexMain.client.getNetworkHandler() == null) {
            return;
        }
        VertexMain.client.player.addVelocity(0, 0.0568000030517578, 0);
        // yea that's literally it
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
