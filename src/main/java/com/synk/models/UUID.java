package com.synk.models;

public class UUID {
    public static final UUID New = new UUID("");
    public String uid;

    public UUID(){
        this.uid = "";
    }

    public UUID(String uid){
        this.uid = uid;
    }

    public static UUID Generate(){
        return new UUID(java.util.UUID.randomUUID().toString());
    }
}
