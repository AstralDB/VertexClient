/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.module.impl.world;

import vertex.client.VertexMain;
import vertex.client.feature.config.EnumSetting;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.render.Renderer;
import vertex.client.helper.util.Rotations;
import vertex.client.helper.util.Timer;
import net.minecraft.block.Block;
import net.minecraft.client.input.Input;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.awt.Color;

public class AutoLavacast extends Module {

    static boolean moveForwards = false;
    final EnumSetting<Mode> mode = this.config.create(new EnumSetting.Builder<>(Mode.Bypass).name("Mode")
            .description("How to place and move. Bypass is slow but looks legit, fast is VERY speedy")
            .get());
    final Timer timer = new Timer();
    Input original;
    Vec3i incr;
    BlockPos start;

    public AutoLavacast() {
        super("AutoLavacast", "Makes a lavacast", ModuleType.WORLD);
        mode.showIf(() -> !this.isEnabled()); // only show when disabled to prevent changes mid action
    }

    BlockPos getNextPosition() {
        int y = 0;
        while ((y + start.getY()) < VertexMain.client.world.getTopY()) {
            Vec3i ie = incr.multiply(y + 1);
            BlockPos next = start.add(ie).add(0, y, 0);
            if (VertexMain.client.world.getBlockState(next).getMaterial().isReplaceable()) {
                return next;
            }
            y++;
        }
        return null;
    }

    @Override
    public void onFastTick() {
        if (mode.getValue() == Mode.Fast && !timer.hasExpired(100)) {
            return;
        }
        timer.reset();
        BlockPos next = getNextPosition();
        if (next == null) {
            setEnabled(false);
            return;
        }
        Vec3d placeCenter = Vec3d.of(next).add(.5, .5, .5);
        if (mode.getValue() == Mode.Bypass) {
            Rotations.lookAtPositionSmooth(placeCenter, 6);
            if (((VertexMain.client.player.horizontalCollision && moveForwards) || VertexMain.client.player.getBoundingBox()
                    .intersects(Vec3d.of(next), Vec3d.of(next).add(1, 1, 1))) && VertexMain.client.player.isOnGround()) {
                VertexMain.client.player.jump();
                VertexMain.client.player.setOnGround(false);
            }
        }

        if (placeCenter.distanceTo(VertexMain.client.player.getCameraPosVec(1)) < VertexMain.client.interactionManager.getReachDistance()) {
            moveForwards = false;

            ItemStack is = VertexMain.client.player.getInventory().getMainHandStack();
            if (is.isEmpty()) {
                return;
            }
            if (is.getItem() instanceof BlockItem bi) {
                Block p = bi.getBlock();
                if (p.getDefaultState().canPlaceAt(VertexMain.client.world, next)) {
                    VertexMain.client.execute(() -> {
                        BlockHitResult bhr = new BlockHitResult(placeCenter, Direction.DOWN, next, false);
                        VertexMain.client.interactionManager.interactBlock(VertexMain.client.player, Hand.MAIN_HAND, bhr);
                        if (mode.getValue() == Mode.Fast) {
                            Vec3d goP = Vec3d.of(next).add(0.5, 1.05, 0.5);
                            VertexMain.client.player.updatePosition(goP.x, goP.y, goP.z);
                        }
                    });
                }
            }

        } else {
            moveForwards = true;
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void enable() {
        if (original == null) {
            original = VertexMain.client.player.input;
        }
        if (mode.getValue() == Mode.Bypass) {
            VertexMain.client.player.input = new ListenInput();
        }
        incr = VertexMain.client.player.getMovementDirection().getVector();
        start = VertexMain.client.player.getBlockPos();
    }

    @Override
    public void disable() {
        VertexMain.client.player.input = original;
    }

    @Override
    public String getContext() {
        return null;
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {
        BlockPos next = getNextPosition();
        Renderer.R3D.renderOutline(Vec3d.of(start), new Vec3d(1, 0.01, 1), Color.RED, matrices);
        if (next != null) {
            Renderer.R3D.renderOutline(Vec3d.of(next), new Vec3d(1, 1, 1), Color.BLUE, matrices);
        }
    }

    @Override
    public void onHudRender() {

    }

    public enum Mode {
        Bypass, Fast
    }

    static class ListenInput extends Input {
        @Override
        public void tick(boolean slowDown, float f) {
            this.movementForward = moveForwards ? 1 : 0;
        }
    }
}
