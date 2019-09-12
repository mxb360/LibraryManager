package com.mxb.librarymanager.dataprocess.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseUtils {
    private static final String  MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    private String user, password;
    private String url;

    private Connection connection;
    private Statement stmt;

    public DataBaseUtils(String user, String password, String database, String host, int port) {
        this.user = user;
        this.password = password;
        url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnocode=true&characterEncoding=UTF-8";
    }

    public boolean connect() {
        try {
            close();

            Class.forName(MYSQL_DRIVER);
            connection = DriverManager.getConnection(url, user, password);
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void close() {
        try {
            stmt.close();
            connection.close();
            stmt = null;
            connection = null;
        } catch (Exception e) {

        }
    }

    public ResultSet query(String querySql) {
        try {
            return stmt.executeQuery(querySql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(String updateSql) {
        try {
            return stmt.executeUpdate(updateSql) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String addSql) {
        try {
            return stmt.executeUpdate(addSql) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(String addSql) {
        try {
            return stmt.executeUpdate(addSql) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
