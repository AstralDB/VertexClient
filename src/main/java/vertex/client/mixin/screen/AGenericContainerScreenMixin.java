/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.mixin.screen;

import vertex.client.feature.module.ModuleRegistry;
import vertex.client.feature.module.impl.movement.InventoryWalk;
import vertex.client.VertexMain;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;


@Mixin(HandledScreen.class)
public abstract class AGenericContainerScreenMixin {

    final KeyBinding arrowRight = new KeyBinding("", GLFW.GLFW_KEY_RIGHT, "");
    final KeyBinding arrowLeft = new KeyBinding("", GLFW.GLFW_KEY_LEFT, "");
    final KeyBinding arrowUp = new KeyBinding("", GLFW.GLFW_KEY_UP, "");
    final KeyBinding arrowDown = new KeyBinding("", GLFW.GLFW_KEY_DOWN, "");
    @Shadow
    protected int x;
    @Shadow
    protected int y;

    boolean keyPressed(KeyBinding bind) {
        return InputUtil.isKeyPressed(VertexMain.client.getWindow().getHandle(), bind.getDefaultKey().getCode());
    }

    void setState(KeyBinding bind) {
        bind.setPressed(keyPressed(bind));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void coffee_preTick(CallbackInfo ci) {
        if (!ModuleRegistry.getByClass(InventoryWalk.class).isEnabled()) {
            return;
        }
        GameOptions go = VertexMain.client.options;
        setState(go.forwardKey);
        setState(go.rightKey);
        setState(go.backKey);
        setState(go.leftKey);
        setState(go.jumpKey);
        setState(go.sprintKey);

        float yawOffset = 0f;
        float pitchOffset = 0f;
        if (keyPressed(arrowRight)) {
            yawOffset += 5f;
        }
        if (keyPressed(arrowLeft)) {
            yawOffset -= 5f;
        }
        if (keyPressed(arrowUp)) {
            pitchOffset -= 5f;
        }
        if (keyPressed(arrowDown)) {
            pitchOffset += 5f;
        }
        Objects.requireNonNull(VertexMain.client.player).setYaw(VertexMain.client.player.getYaw() + yawOffset);
        VertexMain.client.player.setPitch(VertexMain.client.player.getPitch() + pitchOffset);
    }

}
