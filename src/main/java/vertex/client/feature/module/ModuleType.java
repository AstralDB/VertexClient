

package vertex.client.feature.module;

import vertex.client.helper.render.GameTexture;
import vertex.client.helper.render.Texture;

public enum ModuleType {
    RENDER("Render", GameTexture.ICONS_RENDER.getWhere()),
    MOVEMENT("Movement", GameTexture.ICONS_MOVE.getWhere()),
    MISC("Miscellaneous", GameTexture.ICONS_MISC.getWhere()),
    WORLD("World", GameTexture.ICONS_WORLD.getWhere()),
    EXPLOIT("Exploit", GameTexture.ICONS_EXPLOIT.getWhere()),
    UTIL("Utility", GameTexture.ICONS_UTIL.getWhere()),
    ADDON_PROVIDED("Addons", GameTexture.ICONS_ADDON_PROVIDED.getWhere()),
    COMBAT("Combat", GameTexture.ICONS_COMBAT.getWhere());


    final String name;
    final Texture tex;

    ModuleType(String n, Texture tex) {
        this.name = n;
        this.tex = tex;
    }

    public String getName() {
        return name;
    }

    public Texture getTex() {
        return tex;
    }
}
