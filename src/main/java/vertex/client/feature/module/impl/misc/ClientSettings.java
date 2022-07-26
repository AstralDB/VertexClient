

package vertex.client.feature.module.impl.misc;

import vertex.client.feature.config.StringSetting;
import vertex.client.feature.gui.notifications.Notification;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;

public class ClientSettings extends Module {
    @Getter
    final StringSetting prefix = this.config.create(new StringSetting.Builder(".").name("Prefix").description("The prefix to use for commands").get());

    public ClientSettings() {
        super("ClientSettings", "Configuration for the client", ModuleType.MISC);
    }

    @Override
    public void tick() {

    }

    @Override
    public void enable() {
        setEnabled(false);
        Notification.create(5000, "ClientSettings", Notification.Type.INFO, "No need to enable this");
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
