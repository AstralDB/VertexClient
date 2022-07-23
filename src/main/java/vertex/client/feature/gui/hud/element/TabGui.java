

package vertex.client.feature.gui.hud.element;

import vertex.client.feature.module.ModuleRegistry;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.font.FontRenderers;
import net.minecraft.client.util.math.MatrixStack;

public class TabGui extends HudElement {
    vertex.client.feature.module.impl.render.TabGui tgui;

    public TabGui() {
        super("Tab gui", 5, 100, 0, ModuleType.values().length * FontRenderers.getRenderer().getMarginHeight() + 4);
        double longest = 0;
        for (ModuleType value : ModuleType.values()) {
            longest = Math.max(FontRenderers.getRenderer().getStringWidth(value.getName()), longest);
        }
        longest = Math.ceil(longest + 1);
        width = 2 + 1.5 + 2 + longest + 3;
    }

    vertex.client.feature.module.impl.render.TabGui getTgui() {
        if (tgui == null) {
            tgui = ModuleRegistry.getByClass(vertex.client.feature.module.impl.render.TabGui.class);
        }
        return tgui;
    }

    @Override
    public void renderIntern(MatrixStack stack) {
        stack.push();
        getTgui().render(stack);
        stack.pop();
    }
}
