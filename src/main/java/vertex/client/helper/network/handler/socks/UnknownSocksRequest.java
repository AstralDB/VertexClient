/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */
package vertex.client.helper.network.handler.socks;

import io.netty.buffer.ByteBuf;

/**
 * An unknown socks request.
 *
 * @see SocksInitRequestDecoder
 * @see SocksAuthRequestDecoder
 * @see SocksCmdRequestDecoder
 */
public final class UnknownSocksRequest extends SocksRequest {

    public UnknownSocksRequest() {
        super(SocksRequestType.UNKNOWN);
    }

    @Override
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        // NOOP
    }
}
