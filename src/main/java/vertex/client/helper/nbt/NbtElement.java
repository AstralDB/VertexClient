

package vertex.client.helper.nbt;

import net.minecraft.nbt.NbtCompound;

public abstract class NbtElement {
    public abstract String toString();

    public abstract void serialize(NbtCompound compound);

    public abstract net.minecraft.nbt.NbtElement get();
}
