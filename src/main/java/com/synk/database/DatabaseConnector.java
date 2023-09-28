package com.synk.database;

import com.synk.models.*;
import com.synk.models.UUID;

import java.sql.*;
import java.util.ArrayList;


public class DatabaseConnector {

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


    public UUID postUser(User user) {
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
            return new UUID(suid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(String email) {
        try {
            CallableStatement cmd = con.prepareCall("CALL getUser (?);");
            cmd.setString(1, email);
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
                user.publicKey = MergeKey.fromString(rs.getString("publicKey"));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public User[] getUsers(String uuid) {
        try {
            CallableStatement cmd = con.prepareCall("CALL getUsers (?);");
            cmd.setString(1, uuid);
            ResultSet rs = cmd.executeQuery();
            ArrayList<User> users = new ArrayList<>();


            while (rs.next()) {
                User user = new User();

                user.uuid.uid = rs.getString("uuid");
                user.name = rs.getString("name");
                user.email = rs.getString("email");
//                user.hash = rs.getString("hash");
                user.status = rs.getString("status");
                user.pfp = rs.getString("pfp");
//                user.privateKey = rs.getString("privateKey");
                user.publicKey = MergeKey.fromString(rs.getString("publicKey"));

                users.add(user);
            }
            return users.toArray(new User[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public UUID getUUID(String email) {
        try {
            CallableStatement cmd = con.prepareCall("CALL getUUID (?);");
            cmd.setString(1, email);

            ResultSet rs = cmd.executeQuery();
            String uuid = null;
            while (rs.next()) {
                uuid = rs.getString("uuid");
            }
            return new UUID(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public Message[] loadMessages(String uuid, String uuid2) {
        try {
            CallableStatement cmd = con.prepareCall("CALL loadMsg (?,?);");
            cmd.setString(1, uuid);
            cmd.setString(2, uuid2);
            ResultSet rs = cmd.executeQuery();
            ArrayList<Message> msgs = new ArrayList<>();


            while (rs.next()) {
                Message msg = new Message();

                msg.uuid.uid = rs.getString("uuid");
                msg.sender.uid = rs.getString("sender");
                msg.reciever.uid = rs.getString("reciever");
                msg.message = rs.getString("message");
                msg.timestamp = rs.getDate("timestamp");
                msg.key = MergeKey.fromString(rs.getString("publicKey"));
                msg.isSender = rs.getBoolean("isSender");
                msg.isRead = rs.getBoolean("isRead");
                msg.isDeleted = rs.getBoolean("isDeleted");

                msgs.add(msg);
            }
            return msgs.toArray(new Message[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void postMessage(Message msg) {
        try {
            CallableStatement cmd = con.prepareCall("CALL postMsg (?,?,?,?,?);");
            cmd.setString(1, msg.subuuid.uid);
            cmd.setString(2, msg.sender.uid);
            cmd.setString(3, msg.reciever.uid);
            cmd.setString(4, msg.message);
            cmd.setString(5, msg.key.toString());


            cmd.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void postContact(Contact contact) {
        try {
            CallableStatement cmd = con.prepareCall("CALL postContact (?,?,?);");
            cmd.setString(1, contact.user.uid);
            cmd.setString(2, contact.contact.uid);
            cmd.setString(3, contact.name);

            cmd.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Contact[] getContacts(String uid) {
        try {
            CallableStatement cmd = con.prepareCall("CALL getContacts (?);");
            cmd.setString(1, uid);

            ResultSet rs = cmd.executeQuery();

            ArrayList<Contact> contacts = new ArrayList<>();
            while (rs.next()) {
                Contact contact = new Contact(
                        new UUID(rs.getString("uuid")),
                        new UUID(rs.getString("user")),
                        new UUID(rs.getString("contact")),
                        rs.getString("name")
                );
                contacts.add(contact);
            }
            return (contacts.toArray(new Contact[0]));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateContact(String user,String uuid, String name) {
        try {
            CallableStatement cmd = con.prepareCall("CALL updateContact (?,?);");
            cmd.setString(1, user);
            cmd.setString(2, uuid);
            cmd.setString(3, name);

            cmd.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteContact(String uuid, String user) {
        try {
            CallableStatement cmd = con.prepareCall("CALL deleteContact (?,?);");
            cmd.setString(1, uuid);
            cmd.setString(2, user);

            cmd.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}