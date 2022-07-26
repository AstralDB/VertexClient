
package vertex.client.helper.network.handler.socks;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.ObjectUtil;

/**
 * An socks auth response.
 *
 * @see SocksAuthRequest
 * @see SocksAuthResponseDecoder
 */
public final class SocksAuthResponse extends SocksResponse {
    private static final SocksSubnegotiationVersion SUBNEGOTIATION_VERSION = SocksSubnegotiationVersion.AUTH_PASSWORD;
    private final SocksAuthStatus authStatus;

    public SocksAuthResponse(SocksAuthStatus authStatus) {
        super(SocksResponseType.AUTH);
        this.authStatus = ObjectUtil.checkNotNull(authStatus, "authStatus");
    }

    /**
     * Returns the {@link SocksAuthStatus} of this {@link SocksAuthResponse}
     *
     * @return The {@link SocksAuthStatus} of this {@link SocksAuthResponse}
     */
    public SocksAuthStatus authStatus() {
        return authStatus;
    }

    @Override
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(SUBNEGOTIATION_VERSION.byteValue());
        byteBuf.writeByte(authStatus.byteValue());
    }
}
