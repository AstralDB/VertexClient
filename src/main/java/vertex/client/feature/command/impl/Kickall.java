

package vertex.client.feature.command.impl;

import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkState;
import net.minecraft.network.listener.ClientLoginPacketListener;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.network.packet.s2c.login.LoginCompressionS2CPacket;
import net.minecraft.network.packet.s2c.login.LoginDisconnectS2CPacket;
import net.minecraft.network.packet.s2c.login.LoginHelloS2CPacket;
import net.minecraft.network.packet.s2c.login.LoginQueryRequestS2CPacket;
import net.minecraft.network.packet.s2c.login.LoginSuccessS2CPacket;
import net.minecraft.text.Text;

import java.net.InetSocketAddress;
import java.util.Optional;

public class Kickall extends Command {
    public Kickall() {
        super("Kickall", "Kicks every single person on an offline server", "kickall");
    }

    @Override
    public void onExecute(String[] args) {
        InetSocketAddress sa = (InetSocketAddress) VertexMain.client.getNetworkHandler().getConnection().getAddress();
        for (PlayerListEntry playerListEntry : VertexMain.client.getNetworkHandler().getPlayerList()) {
            if (playerListEntry.getProfile().equals(VertexMain.client.player.getGameProfile())) {
                continue;
            }
            ClientConnection conn = ClientConnection.connect(sa, VertexMain.client.options.shouldUseNativeTransport());
            conn.setPacketListener(new ClientLoginPacketListener() {
                @Override
                public void onHello(LoginHelloS2CPacket packet) {
                    conn.disconnect(Text.of("your mother"));
                }

                @Override
                public void onSuccess(LoginSuccessS2CPacket packet) {

                }

                @Override
                public void onDisconnect(LoginDisconnectS2CPacket packet) {

                }

                @Override
                public void onCompression(LoginCompressionS2CPacket packet) {

                }

                @Override
                public void onQueryRequest(LoginQueryRequestS2CPacket packet) {

                }

                @Override
                public void onDisconnected(Text reason) {

                }

                @Override
                public ClientConnection getConnection() {
                    return null;
                }
            });
            conn.send(new HandshakeC2SPacket(sa.getHostName(), sa.getPort(), NetworkState.LOGIN));
            conn.send(new LoginHelloC2SPacket(playerListEntry.getProfile().getName(), Optional.empty()));
        }
    }
}
