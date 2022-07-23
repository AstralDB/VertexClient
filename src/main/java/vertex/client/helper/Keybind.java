

package vertex.client.helper;

import vertex.client.VertexMain;
import net.minecraft.client.util.InputUtil;

public record Keybind(int keycode) {

    public boolean isPressed() {
        if (keycode < 0) {
            return false;
        }
        boolean isActuallyPressed = InputUtil.isKeyPressed(VertexMain.client.getWindow().getHandle(), keycode);
        return VertexMain.client.currentScreen == null && isActuallyPressed;
    }
}
