package com.podkolzin.nat.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.podkolzin.nat.Globals;
import com.podkolzin.nat.packet.Packet;
import com.podkolzin.nat.packet.request.*;
import com.podkolzin.nat.packet.response.InitialResponse;
import com.podkolzin.nat.packet.response.*;
import com.podkolzin.nat.packet.response.JoinRoom;
import com.podkolzin.nat.packet.response.SignIn;
import com.podkolzin.nat.packet.response.SignUp;
import com.podkolzin.nat.primitives.GameConfig;
import com.podkolzin.nat.primitives.UserInfo;

import java.io.IOException;

/**
 * Created by podko_000 on 28.02.14.
 */
public class Client extends Listener {
    private int appId;
    private ClientListener listener;
    private com.esotericsoftware.kryonet.Client client;

    public Client(String ip, int appId, ClientListener listener) {
        this.appId = appId;
        this.listener = listener;
        client = new com.esotericsoftware.kryonet.Client();
        Packet.registerAll(client.getKryo());
        listener.register(client.getKryo());
        client.start();
        try {
            client.connect(5000, ip, Globals.TCP_PORT, Globals.UDP_PORT);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        client.addListener(this);
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof InitialResponse) {
            if (object instanceof SignIn) {
                listener.onSignIn((SignIn) object);
            } else if (object instanceof SignUp) {
                listener.onSignUp((SignUp) object);
            }
        } else if (object instanceof JoinRoom) {
            listener.onJoinRoom((JoinRoom) object);
        } else if (object instanceof NatException) {
            listener.onException((NatException) object);
        } else if (object instanceof Packet) {
            listener.onReceive((Packet) object);
        }
    }

    public void signIn(String login, String password) {
        client.sendUDP(new com.podkolzin.nat.packet.request.SignIn(appId, login, password));
    }

    public void signUp(String login, String password) {
        client.sendUDP(new com.podkolzin.nat.packet.request.SignUp(appId, login, password));
    }

    public void signUp(UserInfo info) {
        client.sendUDP(new com.podkolzin.nat.packet.request.SignUp(appId, info));
    }

    public void createRoom(boolean isPrivate) {
        client.sendUDP(new CreateRoom(isPrivate, null));
    }

    public void creteRoom(boolean isPrivate, GameConfig config) {
        client.sendUDP(new CreateRoom(isPrivate, config));
    }

    public void joinRoom(int roomId) {
        client.sendUDP(new com.podkolzin.nat.packet.request.JoinRoom(roomId));
    }

    //public void

}
