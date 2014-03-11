package com.podkolzin.nat.client;

import com.esotericsoftware.kryo.Kryo;
import com.podkolzin.nat.packet.Packet;
import com.podkolzin.nat.packet.response.JoinRoom;
import com.podkolzin.nat.packet.response.NatException;
import com.podkolzin.nat.packet.response.SignIn;
import com.podkolzin.nat.packet.response.SignUp;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.03.14
 * Time: 11:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class ClientListener {
    public abstract void onSignIn(SignIn object);

    public abstract void onSignUp(SignUp object);

    public abstract void onJoinRoom(JoinRoom object);

    public abstract void onException(NatException object);

    public abstract void onReceive(Packet packet);

    public abstract void register(Kryo kryo);
}
