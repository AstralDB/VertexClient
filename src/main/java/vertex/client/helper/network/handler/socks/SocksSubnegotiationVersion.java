

package vertex.client.helper.network.handler.socks;

public enum SocksSubnegotiationVersion {
    AUTH_PASSWORD((byte) 0x01), UNKNOWN((byte) 0xff);

    private final byte b;

    SocksSubnegotiationVersion(byte b) {
        this.b = b;
    }

    /**
     * @deprecated Use {@link #valueOf(byte)} instead.
     */
    @Deprecated
    public static SocksSubnegotiationVersion fromByte(byte b) {
        return valueOf(b);
    }

    public static SocksSubnegotiationVersion valueOf(byte b) {
        for (SocksSubnegotiationVersion code : values()) {
            if (code.b == b) {
                return code;
            }
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return b;
    }
}

