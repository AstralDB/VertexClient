

package vertex.client.mixin.entity;

import vertex.client.feature.module.ModuleRegistry;
import vertex.client.feature.module.impl.movement.IgnoreWorldBorder;
import vertex.client.feature.module.impl.render.FreeLook;
import vertex.client.VertexMain;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Redirect(method = "adjustMovementForCollisions(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Lnet/minecraft/world/World;Ljava/util/List;)Lnet/minecraft/util/math/Vec3d;", at = @At(value = "INVOKE", target = "net/minecraft/world/border/WorldBorder.canCollide(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Z"))
    private static boolean coffee_preventCollision(WorldBorder instance, Entity entity, Box box) {
        return !ModuleRegistry.getByClass(IgnoreWorldBorder.class).isEnabled() && instance.canCollide(entity, box);
    }

    @Redirect(method = "updateVelocity", at = @At(value = "INVOKE", target = "net/minecraft/entity/Entity.getYaw()F"))
    float coffee_updateFreelook(Entity instance) {
        FreeLook fl = FreeLook.instance();
        if (instance.equals(VertexMain.client.player) && fl.isEnabled() && !((boolean) fl.getEnableAA().getValue())) {
            return fl.newyaw;
        }
        return instance.getYaw();
    }
}
