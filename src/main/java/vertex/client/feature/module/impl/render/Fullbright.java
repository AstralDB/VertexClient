

package vertex.client.feature.module.impl.render;

import vertex.client.VertexMain;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.mixinUtil.SimpleOptionDuck;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class Fullbright extends Module {

    double og;

    public Fullbright() {
        super("Fullbright", "Allows you to see in complete darkness", ModuleType.RENDER);
    }

    @Override
    public void tick() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void enable() {
        og = MathHelper.clamp(VertexMain.client.options.getGamma().getValue(), 0, 1);
        // this somehow is a special case and i do not know why, this does work tho so im going to ignore it
        ((SimpleOptionDuck<Double>) (Object) VertexMain.client.options.getGamma()).setValueDirectly(10d);
    }

    @Override
    public void disable() {
        VertexMain.client.options.getGamma().setValue(og);
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
