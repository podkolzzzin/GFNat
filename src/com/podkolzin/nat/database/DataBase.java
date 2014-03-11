package com.podkolzin.nat.database;
import java.sql.*;

/**
 * Created by podko_000 on 01.03.14.
 */
public class DataBase {
    public static Connection connection;
    public static void connect(String connectionString) {
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection to MYSql database failed.");
            System.exit(-1);
        }
    }

    public static ResultSet query(String query) {
        try {
            Statement stmt = DataBase.connection.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet simpleSelect(String table, String field, int val) {
        try {
            PreparedStatement s = connection.prepareStatement("SELECT * FROM "+table+" WHERE "+field+"=?");
            s.setInt(1, val);
            s.execute();
            return s.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet simpleSelect(String table, String field, String val) {
        try {
            PreparedStatement s = connection.prepareStatement("SELECT * FROM "+table+" WHERE "+field+"=?");
            s.setString(1, val);
            s.execute();
            return s.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PreparedStatement prepare(String s) {
        try {
            return connection.prepareStatement(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet selectAll(String table) {
        PreparedStatement stmt =  prepare("SELECT * FROM "+table);

        try {
            stmt.execute();
            return stmt.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return  null;
    }
}
