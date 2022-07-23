package vertex.client.feature.module.impl.combat;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import vertex.client.VertexMain;
import vertex.client.feature.config.RangeSetting;
import vertex.client.feature.config.annotation.Setting;
import vertex.client.feature.config.annotation.VisibilitySpecifier;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.util.Rotations;
import vertex.client.helper.util.Timer;
import vertex.client.helper.util.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Godaura extends Module {
    Entity goal = null;
    int tick = 0;

    public Godaura(String n, String d, ModuleType type) {
        super("Godaura", "Absolutely destroy anyone in pvp combat", ModuleType.COMBAT);
    }

    @Override
    public void tick() {
    }

    @Override
    public void onFastTick() {
    }

    @Override
    public void enable() {
        tick++;
        assert VertexMain.client.player != null;
        Vec3d init = VertexMain.client.player.getPos().add(-4, -4, -4);
        Vec3d goal = init.add(8, 8, 8);
        Box selector = new Box(init, goal);
        int delay = (int) ((Num) 3);
        assert VertexMain.client.world != null;
        for (Entity e : VertexMain.client.world.getEntities()) {
            if (e.getBoundingBox().intersects(selector) && e.getUuid() != Cornos.minecraft.player.getUuid() && e.isAttackable()) {
                this.goal = e;
                break;
            }
        }
        if (tick > delay) {
            tick = 0;
            if (this.goal == null) return;
            if (!this.goal.isAlive()) {
                this.goal = null;
                return;
            }
            Random r = new Random();
            double offX = (r.nextDouble() * 6) - 3;
            double offZ = (r.nextDouble() * 6) - 3;
            Vec3d attv = this.goal.getPos().add(offX, 0, offZ);
            Vec3d playerP = VertexMain.client.player.getPos();
            double coordZ = playerP.y;
            for (int i = (int) Math.min(playerP.y + 7, 255); i > playerP.y; i--) {
                if (!VertexMain.client.world.getBlockState(new BlockPos(attv.x, i, attv.z)).getBlock().is(Blocks.AIR)) {
                    coordZ = i + 1;
                    break;
                }
            }
            if (VertexMain.client.player.getAir() == 0) VertexMain.client.player.jump();
            VertexMain.client.player.updatePosition(attv.x, coordZ, attv.z);

            Utils.Logging.warn(attv.x+", "+coordZ+", "+attv.z);
            Objects.requireNonNull(VertexMain.client.getNetworkHandler()).sendPacket(p);
        }
    }

    @Override
    public void disable() {
        this.goal = null;
    }

    @Override
    public String getContext() {
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {


    }

    @Override
    public void onHudRender() {

    }
}
