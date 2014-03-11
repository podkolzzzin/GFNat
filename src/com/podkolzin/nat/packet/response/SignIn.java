package com.podkolzin.nat.packet.response;

import com.esotericsoftware.kryonet.Connection;
import com.podkolzin.nat.packet.Packet;

/**
 * Created by podko_000 on 25.02.14.
 */
public class SignIn extends InitialResponse {

    public SignIn() {}

    public SignIn(boolean success) {
        super(success);
    }
}
