/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.helper.event;

import vertex.client.helper.event.events.base.Event;
import vertex.client.helper.event.events.base.NonCancellableEvent;
import vertex.client.helper.event.events.BlockEntityRenderEvent;
import vertex.client.helper.event.events.BlockRenderEvent;
import vertex.client.helper.event.events.ChunkRenderQueryEvent;
import vertex.client.helper.event.events.EntityRenderEvent;
import vertex.client.helper.event.events.KeyboardEvent;
import vertex.client.helper.event.events.LoreQueryEvent;
import vertex.client.helper.event.events.MouseEvent;
import vertex.client.helper.event.events.PacketEvent;
import vertex.client.helper.event.events.PlayerNoClipQueryEvent;
import vertex.client.helper.event.events.WorldRenderEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EventType {
    PACKET_SEND(PacketEvent.class),
    PACKET_RECEIVE(PacketEvent.class),
    ENTITY_RENDER(EntityRenderEvent.class),
    BLOCK_ENTITY_RENDER(BlockEntityRenderEvent.class),
    BLOCK_RENDER(BlockRenderEvent.class),
    MOUSE_EVENT(MouseEvent.class),
    LORE_QUERY(LoreQueryEvent.class),
    CONFIG_SAVE(NonCancellableEvent.class),
    NOCLIP_QUERY(PlayerNoClipQueryEvent.class),
    KEYBOARD(KeyboardEvent.class),
    POST_INIT(NonCancellableEvent.class),
    HUD_RENDER(NonCancellableEvent.class),
    GAME_EXIT(NonCancellableEvent.class),
    SHOULD_RENDER_CHUNK(ChunkRenderQueryEvent.class),
    WORLD_RENDER(WorldRenderEvent.class);
    private final Class<? extends Event> expectedType;

    public Class<? extends Event> getExpectedType() {
        return expectedType;
    }
}
