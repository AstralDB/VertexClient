

package vertex.client.feature.module.impl.world;

import vertex.client.VertexMain;
import vertex.client.feature.config.DoubleSetting;
import vertex.client.feature.gui.notifications.Notification;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.render.Renderer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.Color;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AutoTNT extends Module {
    final DoubleSetting placeDistance = this.config.create(new DoubleSetting.Builder(4).name("Place distance")
            .description("How far to place the blocks apart")
            .min(1)
            .max(4)
            .precision(0)
            .get());
    boolean missingTntAck = false;

    public AutoTNT() {
        super("AutoTNT", "Automatically places tnt in a grid", ModuleType.WORLD);
    }

    @Override
    public void tick() {
        int tntSlot = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack is = Objects.requireNonNull(VertexMain.client.player).getInventory().getStack(i);
            if (is.getItem() == Items.TNT) {
                tntSlot = i;
                break;
            }
        }
        if (tntSlot == -1) {
            if (!missingTntAck) {
                Notification.create(6000, "AutoTNT", false, Notification.Type.WARNING, "Ran out of tnt! Get more in your hotbar");
            }
            missingTntAck = true;
            return;
        } else {
            missingTntAck = false;
        }

        Vec3d ppos = VertexMain.client.player.getPos();
        for (double x = -10; x < 11; x++) {
            for (double z = -10; z < 11; z++) {
                List<Map.Entry<BlockPos, Double>> airs = new ArrayList<>();

                for (int y = Objects.requireNonNull(VertexMain.client.world).getTopY(); y > VertexMain.client.world.getBottomY(); y--) {
                    Vec3d currentOffset = new Vec3d(x, y, z);
                    BlockPos bp = new BlockPos(new Vec3d(ppos.x + currentOffset.x, y, ppos.z + currentOffset.z));
                    BlockState bs = VertexMain.client.world.getBlockState(bp);
                    double dist = Vec3d.of(bp).distanceTo(ppos);
                    if (bs.getMaterial().isReplaceable()) {
                        airs.add(new AbstractMap.SimpleEntry<>(bp, dist));
                    }
                }
                airs = airs.stream()
                        .filter(blockPosDoubleEntry -> VertexMain.client.world.getBlockState(blockPosDoubleEntry.getKey().down())
                                .getMaterial()
                                .blocksMovement())
                        .collect(Collectors.toList());
                Map.Entry<BlockPos, Double> best1 = airs.stream().min(Comparator.comparingDouble(Map.Entry::getValue)).orElse(null);
                if (best1 == null) {
                    continue; // just void here, cancel
                }
                BlockPos best = best1.getKey();
                if (VertexMain.client.world.getBlockState(best.down()).getBlock() == Blocks.TNT) {
                    continue; // already placed tnt, cancel
                }
                Vec3d lmao = Vec3d.of(best);
                if (lmao.add(.5, .5, .5).distanceTo(VertexMain.client.player.getCameraPosVec(1)) >= 5) {
                    continue;
                }
                if (shouldPlace(best)) {
                    int finalTntSlot = tntSlot;
                    VertexMain.client.execute(() -> {
                        int sel = VertexMain.client.player.getInventory().selectedSlot;
                        VertexMain.client.player.getInventory().selectedSlot = finalTntSlot;
                        BlockHitResult bhr = new BlockHitResult(lmao, Direction.DOWN, best, false);
                        Objects.requireNonNull(VertexMain.client.interactionManager).interactBlock(VertexMain.client.player, Hand.MAIN_HAND, bhr);
                        VertexMain.client.player.getInventory().selectedSlot = sel;
                    });
                }
            }
        }
    }

    @Override
    public void enable() {
    }

    @Override
    public void disable() {

    }

    @Override
    public String getContext() {
        return missingTntAck ? "Missing tnt!" : null;
    }

    boolean shouldPlace(BlockPos b) {
        return b.getX() % ((int) Math.floor(placeDistance.getValue())) == 0 && b.getZ() % ((int) Math.floor(placeDistance.getValue())) == 0;
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {
        Vec3d ppos = Objects.requireNonNull(VertexMain.client.player).getPos();

        for (double x = -10; x < 11; x++) {
            for (double z = -10; z < 11; z++) {
                List<Map.Entry<BlockPos, Double>> airs = new ArrayList<>();

                for (int y = Objects.requireNonNull(VertexMain.client.world).getTopY(); y > VertexMain.client.world.getBottomY(); y--) {
                    Vec3d currentOffset = new Vec3d(x, y, z);
                    BlockPos bp = new BlockPos(new Vec3d(ppos.x + currentOffset.x, y, ppos.z + currentOffset.z));
                    BlockState bs = VertexMain.client.world.getBlockState(bp);
                    double dist = Vec3d.of(bp).distanceTo(ppos);
                    if (bs.getMaterial().isReplaceable()) {
                        airs.add(new AbstractMap.SimpleEntry<>(bp, dist));
                    }
                }
                airs = airs.stream()
                        .filter(blockPosDoubleEntry -> VertexMain.client.world.getBlockState(blockPosDoubleEntry.getKey().down())
                                .getMaterial()
                                .blocksMovement())
                        .collect(Collectors.toList());
                Map.Entry<BlockPos, Double> best1 = airs.stream().min(Comparator.comparingDouble(Map.Entry::getValue)).orElse(null);
                if (best1 == null) {
                    continue; // just void here, cancel
                }
                BlockPos best = best1.getKey();
                if (VertexMain.client.world.getBlockState(best.down()).getBlock() == Blocks.TNT) {
                    continue; // already placed tnt, cancel
                }
                Vec3d lmao = Vec3d.of(best);
                if (shouldPlace(best)) {
                    Renderer.R3D.renderOutline(lmao, new Vec3d(1, 1, 1), Color.RED, matrices);
                }
            }
        }
    }

    @Override
    public void onHudRender() {

    }
}
