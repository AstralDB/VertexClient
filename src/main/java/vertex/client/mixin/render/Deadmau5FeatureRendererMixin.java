

package vertex.client.mixin.render;

import vertex.client.feature.module.ModuleRegistry;
import vertex.client.feature.module.impl.render.MouseEars;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.feature.Deadmau5FeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(Deadmau5FeatureRenderer.class)
public class Deadmau5FeatureRendererMixin {

    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z"))
    boolean coffee_overwriteNameMatch(String s, Object anObject) {
        if (ModuleRegistry.getByClass(MouseEars.class).isEnabled()) {
            return true;
        }
        return s.equals(anObject);
    }

    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;hasSkinTexture()Z"))
    boolean coffee_overwriteSkin(AbstractClientPlayerEntity abstractClientPlayerEntity) {
        if (ModuleRegistry.getByClass(MouseEars.class).isEnabled()) {
            return true;
        }
        return abstractClientPlayerEntity.hasSkinTexture();
    }
}
