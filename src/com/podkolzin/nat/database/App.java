package com.podkolzin.nat.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by podko_000 on 04.03.14.
 */
public class App {
    public String name;
    public int id;
    public String basic;  //package

    public static ArrayList<App> getAll() {
        ResultSet set = DataBase.selectAll("tapps");
        ArrayList<App> result = new ArrayList<App>();
        try {
            while (set.next()) {
                App t = new App();
                try {
                    t.id = set.getInt("cId");
                    t.name = set.getString("cName");
                    t.basic = set.getString("cPackage");
                    result.add(t);
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }
}
