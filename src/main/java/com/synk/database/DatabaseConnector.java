package com.synk.database;

import com.synk.models.data.LoginCreds;
import com.synk.models.UID;
import com.synk.models.User;

import java.sql.*;


public class DatabaseConnector {

    public static DatabaseConnector instance = new DatabaseConnector();
    Connection con;

    public DatabaseConnector() {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/SynkDB",
                    "root",
                    "pass"
            );

            //con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public UID postUser(User user) {
        try {
            CallableStatement cmd = con.prepareCall("CALL postUser (?,?,?,?,?,?);");
            cmd.setString(1, user.name);
            cmd.setString(2, user.email);
            cmd.setString(3, user.hash);
            cmd.setString(4, user.privateKey);
            cmd.setString(5, user.publicKey.toString());
            cmd.registerOutParameter(6, Types.VARCHAR, 36);


            ResultSet rs = cmd.executeQuery();
            String suid = "";

            while (rs.next()) {
                suid = rs.getString(1);
            }
            return new UID(suid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(LoginCreds loginCreds) {
        try {
            CallableStatement cmd = con.prepareCall("CALL getUser (?);");
            cmd.setString(1, loginCreds.email);
            ResultSet rs = cmd.executeQuery();
            User user = new User();


            while (rs.next()) {
                user.uuid.uid = rs.getString("uuid");
                user.name = rs.getString("name");
                user.email = rs.getString("email");
                user.hash = rs.getString("hash");
                user.status = rs.getString("status");
                user.pfp = rs.getString("pfp");
                user.privateKey = rs.getString("privateKey");
                user.publicKey.fromString(rs.getString("publicKey"));
            }
            return user;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public UID getUUID(String email) {
        try {
            CallableStatement cmd = con.prepareCall("CALL getUUID (?);");
            cmd.setString(1, email);

            ResultSet rs = cmd.executeQuery();
            String uuid = null;
            while (rs.next()) {
                uuid = rs.getString("uuid");
            }
            return new UID (uuid);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}