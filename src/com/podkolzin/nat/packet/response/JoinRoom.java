package com.podkolzin.nat.packet.response;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.03.14
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
public class JoinRoom extends Response {
    public boolean success;
    public JoinRoom(boolean s) {
        this.success = s;
    }

    public JoinRoom() {}
}
