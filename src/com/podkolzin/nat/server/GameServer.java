package com.podkolzin.nat.server;

import com.esotericsoftware.kryonet.Connection;
import com.podkolzin.nat.database.User;
import com.podkolzin.nat.packet.request.CreateRoom;
import com.podkolzin.nat.packet.request.JoinRoom;
import com.podkolzin.nat.packet.request.Request;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 08.03.14
 * Time: 12:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class GameServer {
    private int roomId;
    private int adminId;

    public GameServer(int roomId, int adminId) {
        this.roomId = roomId;
        this.adminId = adminId;
    }


    public abstract boolean onCreateRoom(CreateRoom request, int id, Connection c);

    public abstract boolean onJoinRoom(JoinRoom request, User u, Connection c);

    public abstract void  onReceive(Request request, Connection c);

    public abstract void onClose(Request request, Connection c);

    public int getAdminId() {
        return adminId;
    }

    public int getRoomId() {
        return roomId;
    }


    public abstract void onDisconnect(User u, Connection c);
}
