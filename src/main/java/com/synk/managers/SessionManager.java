package com.synk.managers;

import com.synk.models.Session;
import com.synk.models.UUID;

import java.util.Date;
import java.util.HashMap;

public class SessionManager {
    static HashMap<String, Session> session = new HashMap<>();

    public static UUID AddSession(UUID uuid){
        UUID suid = UUID.Generate();
        session.put(suid.uid, new Session(uuid));
        return suid;
    }

    public static boolean CheckSession(UUID suid){
        return CheckSession(suid.uid);
    }
    public static boolean CheckSession(String suid){
        if(!session.containsKey(suid))
            return false;

        if(new Date().getTime() - session.get(suid).lastUsed.getTime() > 604800000L){//1000 * 60 * 60 * 24 * 7  => 1 week
            session.remove(suid);
            return false;
        }

        session.get(suid).lastUsed = new Date();

        return true;
    }

    public static UUID GetSession(UUID s){
        return GetSession(s.uid);
    }
    public static UUID GetSession(String s){
        return session.get(s).uuid;
    }
}
