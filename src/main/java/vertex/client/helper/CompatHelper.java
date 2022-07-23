

package vertex.client.helper;

import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleRegistry;
import vertex.client.feature.module.impl.movement.NoLevitation;
import vertex.client.VertexMain;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Util;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

public class CompatHelper {
    static boolean anyFound = false;
    static Map<String, Runnable> modsToLookOutFor = Util.make(new HashMap<>(), stringRunnableHashMap -> {
        stringRunnableHashMap.put("meteor-client", () -> disableModule(NoLevitation.class, "Meteor client is loaded"));
    });

    static <T extends Module> void disableModule(Class<T> clazz, String reason) {
        Module c = ModuleRegistry.getByClass(clazz);
        c.setDisabled(true);
        c.setDisabledReason(reason);
    }

    public static void init() {
        modsToLookOutFor.forEach((s, runnable) -> {
            if (FabricLoader.getInstance().isModLoaded(s)) {
                VertexMain.log(Level.WARN, "Found incompatible mod " + s + " loaded, some features might not be available");
                if (!anyFound) {
                    anyFound = true;
                }
                runnable.run();
            }
        });
        if (!wereAnyFound()) {
            VertexMain.log(Level.INFO, "No compatability issues found, all good");
        }
    }

    public static boolean wereAnyFound() {
        return anyFound;
    }
}
