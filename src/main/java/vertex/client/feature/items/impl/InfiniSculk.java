

package vertex.client.feature.items.impl;

import vertex.client.VertexMain;
import vertex.client.feature.items.Item;
import vertex.client.feature.items.Option;
import vertex.client.helper.nbt.NbtArray;
import vertex.client.helper.nbt.NbtGroup;
import vertex.client.helper.nbt.NbtList;
import vertex.client.helper.nbt.NbtObject;
import vertex.client.helper.nbt.NbtProperty;
import vertex.client.helper.util.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

public class InfiniSculk extends Item {
    Option<Integer> strength = new Option<>("charge", 300, Integer.class);

    public InfiniSculk() {
        super("InfiniSculk", "Makes a sculk catalyst that infects a very big region");
    }

    @Override
    public ItemStack generate() {
        HitResult bhr = VertexMain.client.player.raycast(200, 0f, false);
        if (!(bhr instanceof BlockHitResult bhr1)) {
            Utils.Logging.error("Look at a block to infect first");
            return null;
        }
        BlockPos origin = bhr1.getBlockPos();
        BlockState bs = VertexMain.client.world.getBlockState(origin);
        if (!bs.getMaterial().blocksMovement()) {
            Utils.Logging.error("Start block has to be solid");
            return null;
        }
        ItemStack stack = new ItemStack(Items.SCULK_CATALYST);
        NbtObject catalyst = new NbtObject("",
                new NbtProperty("charge", strength.getValue()),
                new NbtProperty("decay_delay", 1),
                new NbtList("facings"),
                NbtArray.create("pos", origin.getX(), origin.getY(), origin.getZ()),
                new NbtProperty("update_delay", 1)
        );
        NbtObject[] l = new NbtObject[32];
        Arrays.fill(l, catalyst);
        NbtGroup root = new NbtGroup(new NbtObject("BlockEntityTag", new NbtList("cursors", l), new NbtProperty("id", "minecraft:sculk_catalyst")));
        stack.setNbt(root.toCompound());
        return stack;
    }
}
