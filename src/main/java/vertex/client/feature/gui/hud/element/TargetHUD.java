/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.gui.hud.element;

import vertex.client.feature.module.impl.render.TargetHud;
import vertex.client.VertexMain;
import vertex.client.feature.module.ModuleRegistry;
import net.minecraft.client.util.math.MatrixStack;

public class TargetHUD extends HudElement {

    public TargetHUD() {
        super(
                "Target HUD",
                VertexMain.client.getWindow().getScaledWidth() / 2f + 10,
                VertexMain.client.getWindow().getScaledHeight() / 2f + 10,
                TargetHud.modalWidth,
                TargetHud.modalHeight
        );
    }

    @Override
    public void renderIntern(MatrixStack stack) {
        ModuleRegistry.getByClass(TargetHud.class).draw(stack);
    }
}
