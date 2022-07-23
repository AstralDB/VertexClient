/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.gui.theme.impl;

import vertex.client.feature.gui.theme.Theme;

import java.awt.Color;

public class Ocean implements Theme {

    static final Color accent = new Color(0xe0a9fc);
    static final Color module = new Color(0xFF171E1F, true);
    static final Color config = new Color(0xFF111A1A, true);
    static final Color active = new Color(201, 100, 252, 255);
    static final Color inactive = new Color(66, 66, 66, 255);

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
