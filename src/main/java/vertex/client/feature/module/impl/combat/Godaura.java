package vertex.client.feature.module.impl.combat;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import vertex.client.VertexMain;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.util.Utils;

import java.util.Random;

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
        int delay = 3;
        assert VertexMain.client.world != null;
        for (Entity e : VertexMain.client.world.getEntities()) {
            if (e.getBoundingBox().intersects(selector) && e.getUuid() != VertexMain.client.player.getUuid() && e.isAttackable()) {
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

            if (VertexMain.client.player.getAir() == 0) VertexMain.client.player.jump();
            VertexMain.client.player.updatePosition(attv.x, coordZ, attv.z);

            Utils.Logging.warn(attv.x+", "+coordZ+", "+attv.z);
        }
    }

    @Override
    public void disable() {
        this.goal = null;
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
