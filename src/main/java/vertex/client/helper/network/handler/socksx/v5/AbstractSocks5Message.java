/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.helper.network.handler.socksx.v5;

import vertex.client.helper.network.handler.socksx.AbstractSocksMessage;
import vertex.client.helper.network.handler.socksx.SocksVersion;

/**
 * An abstract {@link Socks5Message}.
 */
public abstract class AbstractSocks5Message extends AbstractSocksMessage implements Socks5Message {
    @Override
    public final SocksVersion version() {
        return SocksVersion.SOCKS5;
    }
}
