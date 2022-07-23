/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.gui.hud.element;

import vertex.client.VertexMain;
import vertex.client.helper.font.FontRenderers;
import vertex.client.helper.render.Texture;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class Taco extends HudElement {
    public Taco() {
        super("Taco", 0, VertexMain.client.getWindow().getScaledHeight(), 100, 100);
    }

    @Override
    public void renderIntern(MatrixStack stack) {
        if (!vertex.client.feature.command.impl.Taco.config.enabled) {
            return;
        }
        vertex.client.feature.command.impl.Taco.Frame frame = vertex.client.feature.command.impl.Taco.getCurrentFrame();
        if (frame == null) {
            FontRenderers.getRenderer().drawString(stack, "Nichts anzuzeigen", 0, 0, 0xFFFFFF);
            return;
        }
        Texture current = frame.getI();

        RenderSystem.disableBlend();
        RenderSystem.setShaderTexture(0, current);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        DrawableHelper.drawTexture(stack, 0, 0, 0, 0, 0, (int) width, (int) height, (int) width, (int) height);
    }
}
