

package vertex.client.feature.module.impl.misc;

import vertex.client.feature.config.RangeSetting;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.util.Utils;
import net.minecraft.client.util.math.MatrixStack;

public class Test extends Module {
    RangeSetting rs = this.config.create(new RangeSetting.Builder(new RangeSetting.Range(5, 10)).name("among")
            .description("sus")
            .uniformMin(0)
            .uniformMax(20)
            .precision(1)
            .get());

    public Test() {
        super("Test", "Testing stuff with the client, can be ignored", ModuleType.MISC);
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

    @Override
    public void tick() {
        Utils.Logging.message(rs.getValue().toString());
    }
}
