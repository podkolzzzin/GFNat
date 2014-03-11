package com.podkolzin.nat.database;

import com.podkolzin.nat.packet.request.SignIn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by podko_000 on 01.03.14.
 */
public class User {
    public String login;
    private int passwordHash;
    public int id;
    public User() {}

    private static User create(ResultSet set) {
        try {
            set.getRow();
            User u = new User();
            u.login = set.getString("cName");
            u.passwordHash = set.getInt("cPassword");
            u.id = set.getInt("cId");
            return u;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User get(int appId, String login) {
        PreparedStatement stmt = DataBase.prepare("SELECT * FROM tusers WHERE cAppId=? AND cName=?");
        try {
            stmt.setInt(1, appId);
            stmt.setString(2, login);
            stmt.execute();
            return create(stmt.getResultSet());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User get(int id) {
        return create(DataBase.simpleSelect("tusers", "cId", id));
    }

    public static User get(String login) {
        return create(DataBase.simpleSelect("tusers","cName", login));
    }

    public static boolean validate(SignIn object) {
        User u = get(object.login);
        return object.password.hashCode() == u.passwordHash;
    }

    public static boolean add(int appId, String login, String password) {
        User u = get(appId, login);
        if(u!=null) return false;
        PreparedStatement ps = DataBase.prepare("INSERT INTO tusers(cName, cPassword, cAppId) VALUES(?,?,?)");
        try {
            ps.setString(1, login);
            ps.setInt(2,password.hashCode());
            ps.setInt(3,appId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
