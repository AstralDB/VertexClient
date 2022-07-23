

package vertex.client.mixin.render;

import vertex.client.feature.gui.DoesMSAA;
import vertex.client.feature.gui.notifications.hudNotif.HudNotificationRenderer;
import vertex.client.feature.gui.screen.base.ClientScreen;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleRegistry;
import vertex.client.feature.module.impl.render.FreeLook;
import vertex.client.feature.module.impl.render.Zoom;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.WorldRenderEvent;
import vertex.client.helper.render.MSAAFramebuffer;
import vertex.client.helper.render.Renderer;
import vertex.client.helper.util.Rotations;
import vertex.client.helper.util.Utils;
import vertex.client.VertexMain;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GameRenderer.class, priority = 990)
public class GameRendererMixin {

    private boolean vb;
    private boolean dis;

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = Opcodes.GETFIELD, ordinal = 0), method = "renderWorld")
    void coffee_dispatchWorldRender(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
        if (vb) {
            VertexMain.client.options.getBobView().setValue(true);
            vb = false;
        }
        MSAAFramebuffer.use(MSAAFramebuffer.MAX_SAMPLES, () -> {
            for (Module module : ModuleRegistry.getModules()) {
                if (module.isEnabled()) {
                    module.onWorldRender(matrix);
                }
            }
            Events.fireEvent(EventType.WORLD_RENDER, new WorldRenderEvent(matrix));
            Renderer.R3D.renderFadingBlocks(matrix);
        });
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V"))
    void coffee_msaaScreenRender(Screen instance, MatrixStack matrices, int mouseX, int mouseY, float delta) {
        boolean shouldMsaa = false;
        for (Element child : instance.children()) {
            if (child instanceof DoesMSAA) {
                shouldMsaa = true;
                break;
            }
        }
        if (shouldMsaa && !(instance instanceof ClientScreen)) { // only do msaa if we dont already do it and need it
            MSAAFramebuffer.use(MSAAFramebuffer.MAX_SAMPLES, () -> instance.render(matrices, mouseX, mouseY, delta));
        } else {
            instance.render(matrices, mouseX, mouseY, delta);
        }
    }

    @Inject(at = @At("HEAD"), method = "renderWorld")
    private void coffee_preRenderWorld(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
        dis = true;
    }

    @Inject(at = @At("HEAD"), method = "bobView", cancellable = true)
    private void coffee_stopCursorBob(MatrixStack matrices, float f, CallbackInfo ci) {
        if (VertexMain.client.options.getBobView().getValue() && dis) {
            vb = true;
            VertexMain.client.options.getBobView().setValue(false);
            dis = false;
            ci.cancel();
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;raycast(DFZ)Lnet/minecraft/util/hit/HitResult;"), method = "updateTargetedEntity", require = 0)
    HitResult coffee_replaceHitResult(Entity instance, double maxDistance, float tickDelta, boolean includeFluids) {
        if (ModuleRegistry.getByClass(FreeLook.class).isEnabled() && !((boolean) FreeLook.instance().getEnableAA().getValue())) {
            Vec3d vec3d = instance.getCameraPosVec(tickDelta);
            Vec3d vec3d2 = Utils.Math.getRotationVector(Rotations.getClientPitch(), Rotations.getClientYaw());
            Vec3d vec3d3 = vec3d.add(vec3d2.x * maxDistance, vec3d2.y * maxDistance, vec3d2.z * maxDistance);
            return instance.world.raycast(new RaycastContext(
                    vec3d,
                    vec3d3,
                    RaycastContext.ShapeType.OUTLINE,
                    includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE,
                    instance
            ));
        } else {
            return instance.raycast(maxDistance, tickDelta, includeFluids);
        }
    }

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    public void coffee_overwriteFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        double zv = ModuleRegistry.getByClass(Zoom.class).getZoomValue(cir.getReturnValue());
        cir.setReturnValue(zv);
    }

    @Inject(method = "render", at = @At("RETURN"))
    void coffee_renderNotifs(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        HudNotificationRenderer.instance.render(Renderer.R3D.getEmptyMatrixStack());
    }
}
