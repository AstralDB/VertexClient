

package vertex.client.mixin.render;

import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.BlockRenderEvent;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderManager.class)
public class BlockRenderManagerMixin {
    @Inject(method = "renderBlock", at = @At("HEAD"), cancellable = true)
    void coffee_dispatchRenderEvent(BlockState state, BlockPos pos, BlockRenderView world, MatrixStack matrices, VertexConsumer vertexConsumer, boolean cull, Random random, CallbackInfo ci) {
        BlockRenderEvent be = new BlockRenderEvent(matrices, pos, state);
        if (Events.fireEvent(EventType.BLOCK_RENDER, be)) {
            ci.cancel();
        }
    }
}
