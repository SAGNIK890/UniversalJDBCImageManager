package com.spyder.universaljdbcimagemanager;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {

    private Connection con;

    private String tableName;
    private String imageColumn;
    private String pkColumn;

    public void connect(
            String driver,
            String url,
            String username,
            String password,
            String tableName,
            String imageColumn,
            String pkColumn
    ) throws Exception {

        Class.forName(driver);

        con = DriverManager.getConnection(
                url,
                username,
                password
        );

        this.tableName = tableName;
        this.imageColumn = imageColumn;
        this.pkColumn = pkColumn;
    }

    public ResultSet getImages() throws Exception {

        Statement st =
                con.createStatement();

        return st.executeQuery(
                "SELECT " + pkColumn +
                " FROM " + tableName
        );
    }

    public ResultSet getImageById(
            String id
    ) throws Exception {

        String q =
                "SELECT "
                        + imageColumn
                        + " FROM "
                        + tableName
                        + " WHERE "
                        + pkColumn
                        + "=?";

        PreparedStatement ps =
                con.prepareStatement(q);

        ps.setString(1, id);

        return ps.executeQuery();
    }

    public void uploadImage(
            File file
    ) throws Exception {

        String q =
                "INSERT INTO "
                        + tableName
                        + "("
                        + imageColumn
                        + ") VALUES(?)";

        PreparedStatement ps =
                con.prepareStatement(q);

        FileInputStream fis =
                new FileInputStream(file);

        ps.setBinaryStream(
                1,
                fis,
                (int) file.length()
        );

        ps.executeUpdate();

        fis.close();
        ps.close();
    }

    public void deleteImage(
            String id
    ) throws Exception {

        String q =
                "DELETE FROM "
                        + tableName
                        + " WHERE "
                        + pkColumn
                        + "=?";

        PreparedStatement ps =
                con.prepareStatement(q);

        ps.setString(1, id);

        ps.executeUpdate();

        ps.close();
    }
}
