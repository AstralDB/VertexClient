/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.module.impl.movement;

import vertex.client.VertexMain;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

import java.util.Objects;

public class EdgeJump extends Module {

    public EdgeJump() {
        super("EdgeJump", "Jumps automatically at the edges of blocks", ModuleType.MOVEMENT);
    }

    @Override
    public void tick() {
        if (VertexMain.client.player == null || VertexMain.client.world == null) {
            return;
        }
        if (!VertexMain.client.player.isOnGround() || VertexMain.client.player.isSneaking()) {
            return;
        }

        Box bounding = VertexMain.client.player.getBoundingBox();
        bounding = bounding.offset(0, -0.5, 0);
        bounding = bounding.expand(-0.001, 0, -0.001);
        if (!VertexMain.client.world.getBlockCollisions(client.player, bounding).iterator().hasNext()) {
            Objects.requireNonNull(client.player).jump();
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
