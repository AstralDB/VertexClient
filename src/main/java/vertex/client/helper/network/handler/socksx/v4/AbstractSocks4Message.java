/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.helper.network.handler.socksx.v4;


import vertex.client.helper.network.handler.socksx.AbstractSocksMessage;
import vertex.client.helper.network.handler.socksx.SocksVersion;

/**
 * An abstract {@link Socks4Message}.
 */
public abstract class AbstractSocks4Message extends AbstractSocksMessage implements Socks4Message {
    @Override
    public final SocksVersion version() {
        return SocksVersion.SOCKS4a;
    }
}
