/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.helper.event.events;

import vertex.client.helper.event.events.base.NonCancellableEvent;

public class ChunkRenderQueryEvent extends NonCancellableEvent {
    private boolean shouldRender = false;
    private boolean wasModified = false;

    public void setShouldRender(boolean shouldRender) {
        this.shouldRender = shouldRender;
        this.wasModified = true;
    }

    public boolean wasModified() {
        return wasModified;
    }

    public boolean shouldRender() {
        return shouldRender;
    }
}
