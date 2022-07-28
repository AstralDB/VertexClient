

package vertex.client.feature.gui.theme.impl;

import vertex.client.feature.gui.theme.Theme;

import java.awt.Color;

public class Ocean implements Theme {

    static final Color accent = new Color(0xa980bf);
    static final Color module = new Color(0xFF0f0f0f, true);
    static final Color config = new Color(0xFF0f0f0f, true);
    static final Color active = new Color(253, 161, 233, 255);
    static final Color inactive = new Color(42, 42, 42, 255);

    @Override
    public String getName() {
        return "DarkPink";
    }

    @Override
    public Color getAccent() {
        return accent;
    }

    @Override
    public Color getModule() {
        return module;
    }

    @Override
    public Color getConfig() {
        return config;
    }

    @Override
    public Color getActive() {
        return active;
    }

    @Override
    public Color getInactive() {
        return inactive;
    }
}
