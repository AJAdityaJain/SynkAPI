package com.synk.models;

import java.util.Date;

public class Session {
    public Date lastUsed;
    public UUID uuid;

    public Session(UUID uuid) {
        this.lastUsed = new Date();
        this.uuid = uuid;
    }
}
