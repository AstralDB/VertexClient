/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.gui.theme;

import vertex.client.feature.gui.theme.impl.Ocean;

public class ThemeManager {
    static final Theme bestThemeEver = new Ocean();

    public static Theme getMainTheme() {
        return bestThemeEver;
    }
}
