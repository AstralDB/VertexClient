

package vertex.client.feature.gui.screen.base;

import vertex.client.VertexMain;
import vertex.client.helper.render.MSAAFramebuffer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ClientScreen extends Screen {
    final int samples;

    public ClientScreen(int samples) {
        super(Text.of(""));
        this.samples = samples;
    }

    public ClientScreen() {
        this(MSAAFramebuffer.MAX_SAMPLES);
    }

    public void renderInternal(MatrixStack stack, int mouseX, int mouseY, float delta) {
        super.render(stack, mouseX, mouseY, delta);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        VertexMain.client.keyboard.setRepeatEvents(true);
        if (samples != -1) {
            if (!MSAAFramebuffer.framebufferInUse()) {
                MSAAFramebuffer.use(samples, () -> renderInternal(matrices, mouseX, mouseY, delta));
            } else {
                renderInternal(matrices, mouseX, mouseY, delta);
            }
        } else {
            renderInternal(matrices, mouseX, mouseY, delta);
        }
    }
}
