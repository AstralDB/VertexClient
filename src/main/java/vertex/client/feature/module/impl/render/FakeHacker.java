

package vertex.client.feature.module.impl.render;

import vertex.client.VertexMain;
import vertex.client.feature.gui.notifications.Notification;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.MouseEvent;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

public class FakeHacker extends Module {
    PlayerEntity target = null;

    public FakeHacker() {
        super("FakeHacker", "Makes it seem like another user is hacking", ModuleType.RENDER);
        Events.registerEventHandler(EventType.MOUSE_EVENT, event -> {
            if (!this.isEnabled()) {
                return;
            }
            if (VertexMain.client.player == null || VertexMain.client.world == null) {
                return;
            }
            if (VertexMain.client.currentScreen != null) {
                return;
            }
            MouseEvent me = (MouseEvent) event;
            if (me.getAction() == 1 && me.getButton() == 2) {
                HitResult hr = VertexMain.client.crosshairTarget;
                if (hr instanceof EntityHitResult ehr && ehr.getEntity() instanceof PlayerEntity pe) {
                    target = pe;
                }
            }
        });
    }

    @Override
    public void tick() {
        if (target != null) {
            Iterable<Entity> entities = Objects.requireNonNull(VertexMain.client.world).getEntities();
            List<Entity> entities1 = new ArrayList<>(StreamSupport.stream(entities.spliterator(), false).toList());
            Collections.shuffle(entities1);
            for (Entity entity : entities1) {
                if (entity.equals(target)) {
                    continue;
                }
                if (entity.isAttackable() && entity.distanceTo(target) < 4) {
                    target.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, entity.getPos().add(0, entity.getHeight() / 2, 0));
                    target.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }

    @Override
    public void enable() {
        target = null;
        Notification.create(6000, "", true, Notification.Type.INFO, "Middle click a player to select them");
    }

    @Override
    public void disable() {

    }

    @Override
    public String getContext() {
        return target == null ? null : target.getEntityName();
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {

    }

    @Override
    public void onHudRender() {

    }
}
