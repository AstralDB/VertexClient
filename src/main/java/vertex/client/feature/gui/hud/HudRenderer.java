

package vertex.client.feature.gui.hud;

import vertex.client.feature.gui.screen.HudEditorScreen;
import vertex.client.VertexMain;
import vertex.client.feature.gui.hud.element.HudElement;
import vertex.client.feature.gui.hud.element.RadarElement;
import vertex.client.feature.gui.hud.element.SpeedHud;
import vertex.client.feature.gui.hud.element.TabGui;
import vertex.client.feature.gui.hud.element.Taco;
import vertex.client.feature.gui.hud.element.TargetHUD;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.MouseEvent;
import vertex.client.helper.util.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HudRenderer {

    static final File CONFIG = new File(VertexMain.BASE, "hud.sip");
    private static HudRenderer INSTANCE;
    final List<HudElement> elements = register();
    boolean isEditing = false;
    boolean mouseHeldDown = false;
    double prevX = Utils.Mouse.getMouseX();
    double prevY = Utils.Mouse.getMouseY();
    double prevWX = VertexMain.client.getWindow().getScaledWidth();
    double prevWY = VertexMain.client.getWindow().getScaledHeight();

    private HudRenderer() {
        Events.registerEventHandler(EventType.MOUSE_EVENT, event -> {
            if (!isEditing) {
                return;
            }
            MouseEvent me = (MouseEvent) event;
            if (me.getAction() == 1) {
                mouseHeldDown = true;
                prevX = Utils.Mouse.getMouseX();
                prevY = Utils.Mouse.getMouseY();
                for (HudElement element : elements) {
                    if (element.mouseClicked(Utils.Mouse.getMouseX(), Utils.Mouse.getMouseY())) {
                        break;
                    }
                }
            } else if (me.getAction() == 0) {
                mouseHeldDown = false;
                for (HudElement element : elements) {
                    element.mouseReleased();
                }
            }
        });
        Events.registerEventHandler(EventType.CONFIG_SAVE, event -> saveConfig());
        loadConfig();
    }

    public static HudRenderer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HudRenderer();
        }
        return INSTANCE;
    }

    static List<HudElement> register() {
        List<HudElement> he = new ArrayList<>();
        he.add(new TargetHUD());
        he.add(new Taco());
        he.add(new SpeedHud());
        he.add(new TabGui());
        he.add(new RadarElement());
        return he;
    }

    void saveConfig() {
        VertexMain.log(Level.INFO, "Saving hud");
        JsonArray root = new JsonArray();
        for (HudElement element : elements) {
            JsonObject current = new JsonObject();
            current.addProperty("id", element.getId());
            current.addProperty("px", element.getPosX());
            current.addProperty("py", element.getPosY());
            root.add(current);
        }
        try {
            FileUtils.write(CONFIG, root.toString(), StandardCharsets.UTF_8);
        } catch (Exception ignored) {
            VertexMain.log(Level.ERROR, "Failed to write hud file");
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    void loadConfig() {
        VertexMain.log(Level.INFO, "Loading hud");
        if (!CONFIG.isFile()) {
            CONFIG.delete();
        }
        if (!CONFIG.exists()) {
            VertexMain.log(Level.INFO, "Skipping hud loading because file doesn't exist");
            return;
        }
        try {
            String contents = FileUtils.readFileToString(CONFIG, StandardCharsets.UTF_8);
            JsonArray ja = JsonParser.parseString(contents).getAsJsonArray();
            for (JsonElement jsonElement : ja) {
                JsonObject jo = jsonElement.getAsJsonObject();
                String id = jo.get("id").getAsString();
                int x = jo.get("px").getAsInt();
                int y = jo.get("py").getAsInt();
                for (HudElement element : elements) {
                    if (element.getId().equalsIgnoreCase(id)) {
                        element.setPosX(x);
                        element.setPosY(y);
                    }
                }
            }
        } catch (Exception ignored) {
            VertexMain.log(Level.ERROR, "Failed to read hud file - corrupted?");
        }
        VertexMain.log(Level.INFO, "Loaded hud");
    }

    public void fastTick() {
        double currentWX = VertexMain.client.getWindow().getScaledWidth();
        double currentWY = VertexMain.client.getWindow().getScaledHeight();
        if (currentWX != prevWX) {
            for (HudElement element : elements) {
                double px = element.getPosX();
                double perX = px / prevWX;
                element.setPosX(currentWX * perX);
            }
            prevWX = currentWX;
        }
        if (currentWY != prevWY) {
            for (HudElement element : elements) {
                double py = element.getPosY();
                double perY = py / prevWY;
                element.setPosY(currentWY * perY);
            }
            prevWY = currentWY;
        }
        isEditing = VertexMain.client.currentScreen instanceof HudEditorScreen;
        if (mouseHeldDown) {
            for (HudElement element : elements) {
                element.mouseDragged(Utils.Mouse.getMouseX() - prevX, Utils.Mouse.getMouseY() - prevY);
            }
            prevX = Utils.Mouse.getMouseX();
            prevY = Utils.Mouse.getMouseY();
        }
        for (HudElement element : elements) {
            element.fastTick();
        }
    }

    public void render() {
        for (HudElement element : elements) {
            element.render();
            if (isEditing) {
                element.renderOutline();
            }
        }
    }
}
