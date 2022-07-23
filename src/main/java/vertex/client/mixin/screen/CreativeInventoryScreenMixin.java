/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.mixin.screen;

import vertex.client.feature.gui.screen.NbtEditorScreen;
import vertex.client.feature.gui.widget.RoundButton;
import vertex.client.helper.util.Utils;
import vertex.client.VertexMain;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public class CreativeInventoryScreenMixin extends Screen {

    protected CreativeInventoryScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    void coffee_postInit(CallbackInfo ci) {
        RoundButton nbtEdit = new RoundButton(RoundButton.STANDARD, 5, 5, 64, 20, "NBT editor", () -> {
            if (VertexMain.client.player.getInventory().getMainHandStack().isEmpty()) {
                Utils.Logging.error("You need to hold an item!");
            } else {
                VertexMain.client.setScreen(new NbtEditorScreen(VertexMain.client.player.getInventory().getMainHandStack()));
            }
        });
        addDrawableChild(nbtEdit);
    }
}
