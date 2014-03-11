package com.podkolzin.nat.packet.request;

import com.podkolzin.nat.primitives.UserInfo;

/**
 * Created by podko_000 on 28.02.14.
 */
public class SignUp extends Initial {
    public UserInfo info;
    public SignUp() {}

    public SignUp(int appId, String login, String password) {
        super(appId);
        info = new UserInfo(login, password);
    }

    public SignUp(int appId, UserInfo userInfo) {
        super(appId);
        info = userInfo;
    }
}
