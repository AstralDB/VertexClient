/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.mixin.render;

import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Deque;

@Mixin(MatrixStack.class)
public interface IMatrixStackMixin {
    @Accessor("stack")
    Deque<MatrixStack.Entry> getStack();
}
