package com.podkolzin.nat.packet.request;

import com.podkolzin.nat.primitives.GameConfig;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 10.03.14
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public class CreateRoom extends Request {
    public GameConfig config;
    public boolean isPrivate;

    public CreateRoom() {}

    public CreateRoom(boolean isPrivate, GameConfig config) {
        this.isPrivate = isPrivate;
        this.config = config;
    }
}
