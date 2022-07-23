

package vertex.client.mixin;

import vertex.client.feature.command.impl.SelfDestruct;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.KeyboardEvent;
import vertex.client.helper.manager.KeybindingManager;
import vertex.client.VertexMain;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Shadow
    private boolean repeatEvents;

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "onKey", at = @At("RETURN"))
    void coffee_postKeyPressed(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (window == this.client.getWindow()
                .getHandle() && VertexMain.client.currentScreen == null && System.currentTimeMillis() - VertexMain.lastScreenChange > 10 && !SelfDestruct.shouldSelfDestruct()) { // make sure we are in game and the screen has been there for at least 10 ms
            if (VertexMain.client.player == null || VertexMain.client.world == null) {
                return; // again, make sure we are in game and exist
            }
            KeybindingManager.updateSingle(key, action);
            Events.fireEvent(EventType.KEYBOARD, new KeyboardEvent(key, action));
        }
    }

    @Inject(method = "setRepeatEvents", at = @At("HEAD"), cancellable = true)
    void coffee_setRepeatEvents(boolean repeatEvents, CallbackInfo ci) {
        this.repeatEvents = true;
        ci.cancel();
    }
}
