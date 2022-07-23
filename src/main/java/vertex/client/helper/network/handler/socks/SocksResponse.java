
package vertex.client.helper.network.handler.socks;

import io.netty.util.internal.ObjectUtil;

/**
 * An abstract class that defines a SocksResponse, providing common properties for
 * {@link SocksInitResponse}, {@link SocksAuthResponse}, {@link SocksCmdResponse} and {@link UnknownSocksResponse}.
 *
 * @see SocksInitResponse
 * @see SocksAuthResponse
 * @see SocksCmdResponse
 * @see UnknownSocksResponse
 */
public abstract class SocksResponse extends SocksMessage {
    private final SocksResponseType responseType;

    protected SocksResponse(SocksResponseType responseType) {
        super(SocksMessageType.RESPONSE);
        this.responseType = ObjectUtil.checkNotNull(responseType, "responseType");
    }

    /**
     * Returns socks response type
     *
     * @return socks response type
     */
    public SocksResponseType responseType() {
        return responseType;
    }
}
