package com.synk.managers;

import com.synk.models.Session;
import com.synk.models.UID;

import java.util.Date;
import java.util.HashMap;

public class SessionManager {
    static HashMap<String, Session> session = new HashMap<>();

    public static UID AddSession(UID uuid){
        UID suid = new UID();
        session.put(suid.uid, new Session(uuid));
        return suid;
    }

    public static boolean CheckSession(UID suid){
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
}
