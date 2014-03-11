package com.podkolzin.nat.packet.request;

/**
 * Created by podko_000 on 28.02.14.
 */
public class SignIn extends Initial {
    public String login, password;
    public SignIn() {}

    public SignIn(int appId, String login, String password) {
        super(appId);
        this.login = login;
        this.password = password;
    }
}
