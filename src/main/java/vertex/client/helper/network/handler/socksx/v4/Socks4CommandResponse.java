
package vertex.client.helper.network.handler.socksx.v4;

/**
 * A SOCKS4a response.
 */
public interface Socks4CommandResponse extends Socks4Message {

    /**
     * Returns the status of this response.
     */
    Socks4CommandStatus status();

    /**
     * Returns the {@code DSTIP} field of this response.
     */
    String dstAddr();

    /**
     * Returns the {@code DSTPORT} field of this response.
     */
    int dstPort();
}
