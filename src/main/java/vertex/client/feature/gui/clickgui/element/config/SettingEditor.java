

package vertex.client.feature.gui.clickgui.element.config;

import vertex.client.feature.config.SettingBase;
import vertex.client.feature.gui.clickgui.ClickGUI;
import vertex.client.feature.gui.element.Element;
import vertex.client.helper.render.Rectangle;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;

public abstract class SettingEditor<C extends SettingBase<?>> extends Element {
    @Getter
    final C configValue;

    public SettingEditor(double x, double y, double width, double height, C confValue) {
        super(x, y, width, height);
        this.configValue = confValue;
    }

    @Override
    public boolean mouseScrolled(double x, double y, double amount) {
        return false;
    }

    @Override
    public void render(MatrixStack stack, double mouseX, double mouseY) {
        if (new Rectangle(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight()).contains(mouseX, mouseY)) {
            ClickGUI.instance().setTooltip(configValue.description);
        }
    }

    @Override
    public boolean isActive() {
        return configValue.shouldShow();
    }
}
