package com.synk.models;

import org.springframework.lang.Nullable;

public class User
{
    public String uuid;
    public String name;
    public String status;
    public String email;
    public String hash;
    public String privateKey;
    public MergeKey publicKey;
    @Nullable
    public String pfp;
    public User(){
        uuid = "";
        name = "";
        status = "";
        email = "";
        hash = "";
        privateKey = "";
        publicKey = new MergeKey();
    }
}