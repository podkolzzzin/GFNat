package com.podkolzin.nat.packet.response;

/**
 * Created by podko_000 on 01.03.14.
 */
public abstract class InitialResponse extends Response {
    public boolean success;

    public InitialResponse() {}

    public InitialResponse(boolean success) {
        this.success = success;
    }
}
