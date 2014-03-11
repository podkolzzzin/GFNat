package com.podkolzin.nat.packet.request;

/**
 * Created by podko_000 on 28.02.14.
 */
public abstract class Initial extends Request {
    public int appId;
    public Initial() {}

    public Initial(int appId) {
        this.appId = appId;
    }
}
