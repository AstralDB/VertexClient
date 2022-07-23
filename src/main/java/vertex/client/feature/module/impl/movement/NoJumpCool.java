/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.module.impl.movement;

import vertex.client.VertexMain;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.mixin.entity.ILivingEntityMixin;
import net.minecraft.client.util.math.MatrixStack;

public class NoJumpCool extends Module {

    public NoJumpCool() {
        super("NoJumpCool", "Removes the jump cooldown", ModuleType.MOVEMENT);
    }

    @Override
    public void tick() {
        if (VertexMain.client.player == null || VertexMain.client.getNetworkHandler() == null) {
            return;
        }
        ((ILivingEntityMixin) VertexMain.client.player).setJumpingCooldown(0);
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
