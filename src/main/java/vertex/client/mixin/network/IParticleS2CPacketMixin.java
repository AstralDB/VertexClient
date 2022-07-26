

package vertex.client.mixin.network;

import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ParticleS2CPacket.class)
public interface IParticleS2CPacketMixin {
    @Accessor("count")
    @Mutable
    void setCount(int count);
}
