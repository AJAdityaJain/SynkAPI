package com.synk.models;

import java.util.Date;

public class Message {
    public UUID uuid;
    public UUID subuuid;
    public UUID sender;
    public UUID reciever;
    public String message;
    public Date timestamp;
    public MergeKey key;
    public boolean isSender ;
    public boolean isRead;
    public boolean isDeleted;

    public Message(UUID subuuid, UUID sender, UUID reciever,String message, String key)
    {
        this.subuuid = subuuid;
        this.uuid = UUID.New;
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.key = MergeKey.fromString(key);

        this.timestamp = new Date();
        this.isSender = false;
        isRead = false;
        isDeleted = false;
    }
    public Message()
    {
        this.uuid = UUID.New;
        this.sender = UUID.New;
        this.reciever = UUID.New;
        this.message = "";
        this.key = new MergeKey();

        this.timestamp = new Date();
        this.isSender = false;
        isRead = false;
        isDeleted = false;
    }
}