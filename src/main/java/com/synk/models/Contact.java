package com.synk.models;

public class Contact {
    public UUID uuid;
    public UUID user;
    public UUID contact;
    public String name;

    public Contact(UUID uuid, UUID user, UUID contact, String name)
    {
        this.uuid = uuid;
        this.user = user;
        this.contact = contact;
        this.name = name;
    }
}
