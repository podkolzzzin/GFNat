package com.podkolzin.nat.server;

import com.esotericsoftware.kryonet.Connection;
import com.podkolzin.nat.Globals;
import com.podkolzin.nat.database.DataBase;
import com.podkolzin.nat.packet.Packet;
import com.podkolzin.nat.packet.request.*;
import com.podkolzin.nat.packet.response.NatException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by podko_000 on 25.02.14.
 */
public class Server {
    private com.esotericsoftware.kryonet.Server server;
    private HashMap<Integer, RouterServer> routers;
    private HashMap<Integer, Integer> connectionApps = new HashMap<Integer, Integer>(); //bind app and connectionId

    private Server() {
        DataBase.connect("jdbc:mysql://localhost:3306/gfnat?user=root");
        routers = RouterServer.getList();

        server = new com.esotericsoftware.kryonet.Server();
        try {
            server.bind(Globals.TCP_PORT, Globals.UDP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Packet.registerAll(server.getKryo());

        server.addListener(new ServerListener(this));
        server.start();
    }

    private boolean check(Connection connection, Integer appId, RouterServer router) {
        appId = connectionApps.get(connection.getID());
        if (appId == null) {
            connection.sendUDP(NatException.NoBindingToApp());
            return false;
        }

        router = routers.get(appId);
        if (router == null) {
            connection.sendUDP(NatException.AppIdIsInvalid());
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        new Server();
    }

    public void routeToGameServer(Request request, Connection connection) {

        if (request instanceof Initial) {
            Initial initial = (Initial) request;
            connectionApps.put(connection.getID(), initial.appId);
            routers.get(initial.appId).route(initial, connection);
        } else {
            RouterServer router = null;
            Integer appId = null;
            if (!check(connection, appId, router))
                return;

            if (request instanceof CreateRoom) {
                router.route((CreateRoom) request, connection);
            } else if (request instanceof JoinRoom) {
                router.route((JoinRoom) request, connection);
            } else {
                router.route(request, connection);
            }
        }
    }

    public void disconnected(Connection connection) {
        RouterServer router = null;
        Integer appId = null;
        if (!check(connection, appId, router))
            return;

        connectionApps.remove(connection.getID());

        router.disconnected(connection);
    }
}
