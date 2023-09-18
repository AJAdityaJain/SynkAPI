package com.synk.websocket;
/*
 * Copyright (c) 2010-2020 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.synk.Application;
import com.synk.managers.SessionManager;
import com.synk.models.Message;
import com.synk.models.UUID;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class SocketServer extends WebSocketServer {
    HashMap<WebSocket, UUID> connections = new HashMap<>();

    public SocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    public SocketServer(InetSocketAddress address) {
        super(address);
    }

    public SocketServer(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.singletonList(draft));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!");
        System.out.println(
                conn + " entered the room!");
    }


    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        connections.remove(conn);
        System.out.println(conn + " has left the room!");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        try {
            if (!connections.containsKey(conn)) {
                if (SessionManager.CheckSession(message))
                    connections.put(conn, SessionManager.GetSession(message));
            } else {
                String[] msg = message.split("#", 4);//code#reciever#key#message
                String id = connections.get(conn).uid;

                switch (msg[0]) {
                    case "0": {
                        ArrayList<WebSocket> recievers = new ArrayList<>();
                        ArrayList<WebSocket> senders = new ArrayList<>();

                        for (WebSocket item : connections.keySet()) {
                            if (connections.get(item).uid.equals(msg[1])) {
                                recievers.add(item);
                            }
                            if (connections.get(item).uid.equals(id)) {
                                senders.add(item);
                            }
                        }

                        Message m = new Message(UUID.Generate(),new UUID(id), new UUID(msg[1]), msg[3], msg[2]);
                        Application.db.postMessage(m);


                        m.isSender = true;

                        broadcast(Application.json.writeValueAsString(m), recievers);

                        m.reciever = new UUID(id);
                        m.sender = new UUID(msg[1]);
                        m.isSender = false;

                        broadcast(Application.json.writeValueAsString(m), senders);
                        break;
                    }
                    case "1":{
                        ArrayList<WebSocket> recievers = new ArrayList<>();
                        for (WebSocket item : connections.keySet()) {
                            if (connections.get(item).uid.equals(msg[1])) {
                                recievers.add(item);
                            }
                        }

                        broadcast("READ#"+id,recievers);
                        break;
                    }
                }
            }
        } catch (JsonProcessingException e) {
            onError(conn, e);
        }
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
//        broadcast(message.array());
//        System.out.println(conn + ": " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        connections.remove(conn);
        ex.printStackTrace();
//      if (conn != null) {
//          some errors like port binding failed may not be assignable to a specific websocket
//      }
    }


    @Override
    public void onStart() {
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }
}
//            connections.forEach((key, value) -> System.out.println(key + " " + value));