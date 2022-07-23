/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.module.impl.world;

import vertex.client.VertexMain;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.util.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class AutoIgnite extends Module {
    public AutoIgnite() {
        super("AutoIgnite", "Automatically ignites any tnt in sight", ModuleType.WORLD);
    }

    int getLighterSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack is = VertexMain.client.player.getInventory().getStack(i);
            if (is.getItem() == Items.FLINT_AND_STEEL) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void tick() {
        int lighterSlot = getLighterSlot();
        if (lighterSlot == -1) {
            return;
        }
        double searchRad = Math.ceil(VertexMain.client.interactionManager.getReachDistance());
        List<BlockPos> blocksToIgnite = new ArrayList<>();
        for (double x = -searchRad; x < searchRad; x++) {
            for (double y = -searchRad; y < searchRad; y++) {
                for (double z = -searchRad; z < searchRad; z++) {
                    Vec3d vPos = VertexMain.client.player.getEyePos().add(x, y, z);
                    if (vPos.distanceTo(VertexMain.client.player.getEyePos()) > VertexMain.client.interactionManager.getReachDistance()) {
                        continue;
                    }
                    BlockPos bp = new BlockPos(vPos);
                    BlockState bs = VertexMain.client.world.getBlockState(bp);
                    if (bs.getBlock() == Blocks.TNT) {
                        blocksToIgnite.add(bp);
                    }
                }
            }
        }
        if (blocksToIgnite.isEmpty()) {
            return;
        }
        int prevSlot = VertexMain.client.player.getInventory().selectedSlot;
        VertexMain.client.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(lighterSlot));

        for (BlockPos blockPos : blocksToIgnite) {
            BlockHitResult bhr = new BlockHitResult(Vec3d.of(blockPos).add(0.5, 0.5, 0.5), Direction.DOWN, blockPos, false);
            PlayerInteractBlockC2SPacket interact = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND,
                    bhr,
                    Utils.increaseAndCloseUpdateManager(VertexMain.client.world)
            );
            VertexMain.client.getNetworkHandler().sendPacket(interact);
        }

        VertexMain.client.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(prevSlot));
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public String getContext() {
        return null;
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {

    }

    @Override
    public void onHudRender() {

    }
}
