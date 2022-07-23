/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.mixin;

import vertex.client.feature.module.impl.util.AntiCrash;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Style.class)
public class StyleMixin {
    @Inject(method = "isObfuscated", at = @At("HEAD"), cancellable = true)
    void coffee_replaceObfuscatedFlag(CallbackInfoReturnable<Boolean> cir) {
        if (AntiCrash.instance().isEnabled() && AntiCrash.instance().getDisableObfText().getValue()) {
            cir.setReturnValue(false);
        }
    }
}
