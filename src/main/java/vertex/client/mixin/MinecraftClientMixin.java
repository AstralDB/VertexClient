

package vertex.client.mixin;

import vertex.client.feature.command.impl.SelfDestruct;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleRegistry;
import vertex.client.feature.module.impl.world.FastUse;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.base.NonCancellableEvent;
import vertex.client.helper.manager.ConfigManager;
import vertex.client.VertexMain;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.crash.CrashReport;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    private int itemUseCooldown;

    @Inject(method = "printCrashReport", at = @At("HEAD"))
    private static void coffee_printCrash(CrashReport report, CallbackInfo ci) {
        List<String> strings = ModuleRegistry.getModules().stream().filter(Module::isEnabled).map(Module::getName).toList();
        report.addElement("Coffee client")
                .add("Enabled modules", strings.isEmpty() ? "None" : String.join(", ", strings.toArray(String[]::new)));
    }

    @Shadow
    public abstract CrashReport addDetailsToCrashReport(CrashReport report);

    @Inject(method = "stop", at = @At("HEAD"))
    void coffee_dispatchExit(CallbackInfo ci) {
        ConfigManager.saveState();
        Events.fireEvent(EventType.GAME_EXIT, new NonCancellableEvent());
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    void coffee_postWindowInit(RunArgs args, CallbackInfo ci) {
        VertexMain.INSTANCE.postWindowInit();
    }

    @Inject(method = "setScreen", at = @At("HEAD"))
    void coffee_setScreenChange(Screen screen, CallbackInfo ci) {
        VertexMain.lastScreenChange = System.currentTimeMillis();
    }

    @Redirect(method = "handleInputEvents", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/MinecraftClient;itemUseCooldown:I"))
    public int coffee_replaceItemUseCooldown(MinecraftClient minecraftClient) {
        if (Objects.requireNonNull(ModuleRegistry.getByClass(FastUse.class)).isEnabled()) {
            return 0;
        } else {
            return this.itemUseCooldown;
        }
    }

    @Inject(method = "getGameVersion", at = @At("HEAD"), cancellable = true)
    void coffee_replaceGameVersion(CallbackInfoReturnable<String> cir) {
        if (SelfDestruct.shouldSelfDestruct()) {
            cir.setReturnValue(SharedConstants.getGameVersion().getName());
        }
    }

    @Inject(method = "getVersionType", at = @At("HEAD"), cancellable = true)
    void coffee_replaceVersionType(CallbackInfoReturnable<String> cir) {
        if (SelfDestruct.shouldSelfDestruct()) {
            cir.setReturnValue("release");
        }
    }

}
