

package vertex.client.helper.network.handler.socks;

public enum SocksAuthStatus {
    SUCCESS((byte) 0x00), FAILURE((byte) 0xff);

    private final byte b;

    SocksAuthStatus(byte b) {
        this.b = b;
    }

    /**
     * @deprecated Use {@link #valueOf(byte)} instead.
     */
    @Deprecated
    public static SocksAuthStatus fromByte(byte b) {
        return valueOf(b);
    }

    public static SocksAuthStatus valueOf(byte b) {
        for (SocksAuthStatus code : values()) {
            if (code.b == b) {
                return code;
            }
        }
        return FAILURE;
    }

    public byte byteValue() {
        return b;
    }
}

