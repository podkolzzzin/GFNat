package com.podkolzin.nat.packet.response;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.03.14
 * Time: 10:10
 * To change this template use File | Settings | File Templates.
 */
public class NatException extends Response {
    public String message;
    public int code;
    public static final int ROOM_NOT_EXISTS = 1;
    public static final int APP_ID_IS_INVALID = 2;
    public static final int NOT_SIGNED_ID = 3;
    public static final int FAILED_BY_SERVER = 4;
    public static final int NOT_JOINED_TO_ROOM = 5;
    public static final int NOT_ADMIN = 6;
    public static final int NO_BINDING_TO_APP = 7;

    public static NatException RoomNotExists() {
        NatException e = new NatException();
        e.code = ROOM_NOT_EXISTS;
        e.message = "Room doesn't exist. Or appId is invalid.";
        return e;
    }

    public static NatException AppIdIsInvalid() {
        NatException e = new NatException();
        e.code = APP_ID_IS_INVALID;
        e.message = "App id is invalid.";
        return e;
    }

    public static NatException NotSignedIn() {
        NatException e = new NatException();
        e.code = NOT_SIGNED_ID;
        e.message = "Sign in before join to room.";
        return e;
    }

    public static NatException FailedByServer() {
        NatException e = new NatException();
        e.code = FAILED_BY_SERVER;
        e.message = "Connection to room is failed. AppServer returns that connection is not success.";
        return e;
    }

    public static NatException NotJoinedToRoom() {
        NatException e = new NatException();
        e.code = NOT_JOINED_TO_ROOM;
        e.message = "It is impossible to send game process data packets before you have joined to room.";
        return e;
    }

    public static NatException NotAdmin() {
        NatException e = new NatException();
        e.code = NOT_ADMIN;
        e.message = "You are not admin of the room. So you can not perform any reconfiguration of the room.";
        return e;
    }

    public static NatException NoBindingToApp()
    {
        NatException e = new NatException();
        e.code = NO_BINDING_TO_APP;
        e.message = "There is no binding between connection and app. Maybe you haven't sign in before.";
        return e;
    }
}
