package com.podkolzin.nat.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.podkolzin.nat.packet.request.*;
import com.podkolzin.nat.server.Server;

/**
 * Created by podko_000 on 25.02.14.
 */
public class ServerListener extends Listener {
    private Server server;

    public ServerListener(Server s) {
        server = s;
    }

    @Override
    public void connected(Connection connection) {
        // connection.sendUDP(new SignIn(connection));
    }

    @Override
    public void disconnected(Connection connection) {
        server.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Request) {
            server.routeToGameServer((Request) object, connection);
        }
    }
}
