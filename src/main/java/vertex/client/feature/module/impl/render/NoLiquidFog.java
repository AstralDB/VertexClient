

package vertex.client.feature.module.impl.render;

import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import net.minecraft.client.util.math.MatrixStack;

public class NoLiquidFog extends Module {
    public static NoLiquidFog INSTANCE;

    public NoLiquidFog() {
        super("NoLiquidFog", "Removes the fogging effects of when you're in water or lava", ModuleType.RENDER);
        INSTANCE = this;
    }

    @Override
    public void tick() {

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
