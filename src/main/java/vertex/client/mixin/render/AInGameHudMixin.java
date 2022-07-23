

package vertex.client.mixin.render;

import vertex.client.feature.gui.notifications.NotificationRenderer;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleRegistry;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.base.NonCancellableEvent;
import vertex.client.helper.render.MSAAFramebuffer;
import vertex.client.helper.util.AccurateFrameRateCounter;
import vertex.client.helper.util.Utils;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class AInGameHudMixin extends DrawableHelper {
    @Inject(method = "render", at = @At("RETURN"))
    public void coffee_runRenderers(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        AccurateFrameRateCounter.globalInstance.recordFrame();
        MSAAFramebuffer.use(MSAAFramebuffer.MAX_SAMPLES, () -> {
            Utils.TickManager.render();
            for (Module module : ModuleRegistry.getModules()) {
                if (module.isEnabled()) {
                    module.onHudRender();
                }
            }

            NotificationRenderer.render();

            Events.fireEvent(EventType.HUD_RENDER, new NonCancellableEvent());
        });
    }
}
