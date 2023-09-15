package com.synk.models;

public class UID {
    public String uid;

    public UID(String uid){
        this.uid = uid;
    }

    public UID(){
        this.uid = java.util.UUID.randomUUID().toString();
    }
}
