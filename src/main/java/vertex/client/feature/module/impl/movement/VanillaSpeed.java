/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.module.impl.movement;

import vertex.client.feature.config.DoubleSetting;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import net.minecraft.client.util.math.MatrixStack;

public class VanillaSpeed extends Module {

    public final DoubleSetting speed = this.config.create(new DoubleSetting.Builder(3).name("Speed")
            .description("The speed multiplier to apply")
            .min(1)
            .max(10)
            .precision(3)
            .get());

    public VanillaSpeed() {
        super("VanillaSpeed", "Gives you an extreme speed boost", ModuleType.MOVEMENT);
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
