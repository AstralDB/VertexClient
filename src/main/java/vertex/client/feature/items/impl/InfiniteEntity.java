/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.items.impl;

import vertex.client.VertexMain;
import vertex.client.feature.items.Item;
import vertex.client.helper.nbt.NbtGroup;
import vertex.client.helper.nbt.NbtList;
import vertex.client.helper.nbt.NbtObject;
import vertex.client.helper.nbt.NbtProperty;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

public class InfiniteEntity extends Item {

    public InfiniteEntity() {
        super("InfiniteEntity", "Creates an entity that will corrupt the chunk it was generated in");
    }

    @Override
    public ItemStack generate() {
        Vec3d pos = VertexMain.client.player.getPos();
        NbtGroup ng = new NbtGroup(new NbtObject("EntityTag",
                new NbtList("Pos", new NbtProperty(pos.x), new NbtProperty(Double.MAX_VALUE), new NbtProperty(pos.z))
        ));
        NbtCompound nc = ng.toCompound();
        ItemStack is = new ItemStack(Items.COW_SPAWN_EGG);
        is.setNbt(nc);
        return is;
    }
}