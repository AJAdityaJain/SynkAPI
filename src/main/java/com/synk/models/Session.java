package com.synk.models;

import java.util.Date;

public class Session {
    public Date lastUsed;
    public UID uuid;

    public Session(UID uuid) {
        this.lastUsed = new Date();
        this.uuid = uuid;
    }
}
