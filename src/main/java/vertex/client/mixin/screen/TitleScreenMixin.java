

package vertex.client.mixin.screen;

import vertex.client.feature.command.impl.SelfDestruct;
import vertex.client.feature.gui.screen.LoadingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    public TitleScreenMixin() {
        super(Text.of(""));
    }

    @Inject(method = "init", at = @At("RETURN"))
    void coffee_postInit(CallbackInfo ci) {
        if (SelfDestruct.shouldSelfDestruct()) {
            return;
        }
        Objects.requireNonNull(client).setScreen(LoadingScreen.instance());
    }
}
