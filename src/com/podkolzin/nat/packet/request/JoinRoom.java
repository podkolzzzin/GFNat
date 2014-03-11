package com.podkolzin.nat.packet.request;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 10.03.14
 * Time: 22:30
 * To change this template use File | Settings | File Templates.
 */
public class JoinRoom extends Request {
    public int roomId;

    public JoinRoom() {}

    public JoinRoom(int roomId) {
        this.roomId = roomId;
    }
}
