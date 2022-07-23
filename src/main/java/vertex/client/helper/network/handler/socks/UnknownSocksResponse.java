/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */
package vertex.client.helper.network.handler.socks;

import io.netty.buffer.ByteBuf;

/**
 * An unknown socks response.
 *
 * @see SocksInitResponseDecoder
 * @see SocksAuthResponseDecoder
 * @see SocksCmdResponseDecoder
 */
public final class UnknownSocksResponse extends SocksResponse {

    public UnknownSocksResponse() {
        super(SocksResponseType.UNKNOWN);
    }

    @Override
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        // NOOP
    }
}
