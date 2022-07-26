

package vertex.client.helper.network.handler.socks;

public enum SocksAddressType {
    IPv4((byte) 0x01), DOMAIN((byte) 0x03), IPv6((byte) 0x04), UNKNOWN((byte) 0xff);

    private final byte b;

    SocksAddressType(byte b) {
        this.b = b;
    }

    /**
     * @deprecated Use {@link #valueOf(byte)} instead.
     */
    @Deprecated
    public static SocksAddressType fromByte(byte b) {
        return valueOf(b);
    }

    public static SocksAddressType valueOf(byte b) {
        for (SocksAddressType code : values()) {
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

