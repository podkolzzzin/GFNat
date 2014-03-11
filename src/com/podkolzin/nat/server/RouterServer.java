package com.podkolzin.nat.server;

import com.esotericsoftware.kryonet.Connection;
import com.podkolzin.nat.database.App;
import com.podkolzin.nat.database.User;
import com.podkolzin.nat.helper.JarLoader;
import com.podkolzin.nat.helper.RoomIdFactory;
import com.podkolzin.nat.packet.request.*;
import com.podkolzin.nat.packet.request.CreateRoom;
import com.podkolzin.nat.packet.request.JoinRoom;
import com.podkolzin.nat.packet.request.SignIn;
import com.podkolzin.nat.packet.request.SignUp;
import com.podkolzin.nat.packet.response.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by podko_000 on 01.03.14.
 */
public class RouterServer {
    private App app;
    private HashMap<Integer, GameServer> servers = new HashMap<Integer, GameServer>();      //bind roomId and GameServer(AppServer) id
    private HashMap<Integer, User> users = new HashMap<Integer, User>(); //bind connection(Kryo) id and User from DB
    private HashMap<Integer, Integer> roomUsers = new HashMap<Integer, Integer>(); //bind player in the room and user id

    private RouterServer(App a) {
        this.app = a;
        File f = new File("servers/" + a.id + "/server.jar");
        try {
            JarLoader.addFile(f);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private GameServer createServer() {
        String name = app.basic + ".AppServer";
        System.out.println(name + " has created");
        try {
            return (GameServer) Class.forName(name).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    public static HashMap<Integer, RouterServer> getList() {
        HashMap<Integer, RouterServer> map = new HashMap<Integer, RouterServer>();
        ArrayList<App> apps = App.getAll();
        for (App a : apps) {
            map.put(a.id, new RouterServer(a));
        }
        return map;
    }

    public void route(Initial request, Connection connection) {
        if (request instanceof SignIn) {
            User u = User.get(request.appId, ((SignIn) request).login);
            if (u != null) {
                users.put(connection.getID(), u);
                connection.sendUDP(new com.podkolzin.nat.packet.response.SignIn(true));
            } else {
                connection.sendUDP(new com.podkolzin.nat.packet.response.SignIn(false));
            }
        } else {
            SignUp s = (SignUp) request;
            connection.sendUDP(new com.podkolzin.nat.packet.response.SignUp(User.add(s.appId, s.info.login, s.info.password)));
        }
    }

    public void route(CreateRoom request, Connection c) {
        GameServer server = createServer();
        int id = RoomIdFactory.nextId();
        if (server.onCreateRoom(request, id, c)) {
            servers.put(id, server);
        }
    }

    public void route(JoinRoom request, Connection c) {
        GameServer s = servers.get(request.roomId);
        if (s != null) {
            User u = users.get(c.getID());
            if (u != null) {
                if (s.onJoinRoom(request, u, c)) {
                    roomUsers.put(u.id, s.getRoomId());
                } else {
                    c.sendUDP(NatException.FailedByServer());
                }
            } else {
                c.sendUDP(NatException.NotSignedIn());
            }
        } else {
            c.sendUDP(NatException.RoomNotExists());
        }
    }

    public void route(Request request, Connection c) {
        User u = users.get(c.getID());
        if (u == null) {
            c.sendUDP(NatException.NotSignedIn());
            return;
        }

        Integer roomId = roomUsers.get(u.id);
        if (roomId == null) {
            c.sendUDP(NatException.NotJoinedToRoom());
        }

        GameServer server = servers.get(roomId);

        if (request instanceof CloseRoom) {
            if (c.getID() == server.getAdminId()) {
                server.onClose(request, c);
                servers.remove(roomId);
            } else {
                c.sendUDP(NatException.NotAdmin());
            }
            return;
        }
        server.onReceive(request, c);
    }

    @Override
    public String toString() {
        return app.id + " " + app.name + " @RouterServer";
    }

    public void disconnected(Connection c) {
        User u = users.get(c.getID());
        if (u == null) {
            return;
        }
        users.remove(c.getID());

        Integer roomId = roomUsers.get(u.id);
        if (roomId == null) {
            return;
        }

        roomUsers.remove(u.id);

        GameServer server = servers.get(roomId);

        if(server.getAdminId()!=c.getID())
            server.onDisconnect(u, c);
        else
            route(new CloseRoom(), c);
    }
}
