/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.module.impl.movement;

import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import net.minecraft.client.util.math.MatrixStack;

public class NoPush extends Module {

    public NoPush() {
        super("NoPush", "Prevents other entities from pushing you around", ModuleType.MOVEMENT);
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

    }

    @Override
    public void onHudRender() {

    }
}
