package com.synk.models;

import org.springframework.lang.Nullable;

public class User {
    public UUID uuid;
    public String name;
    public String status;
    public String email;
    public String hash;
    public String privateKey;
    public MergeKey publicKey;
    @Nullable
    public String pfp;
    public User(){
        uuid = UUID.New;
        name = "";
        status = "";
        email = "";
        hash = "";
        privateKey = "";
        publicKey = new MergeKey();
    }
}