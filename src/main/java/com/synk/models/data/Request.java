package com.synk.models.data;

public class Request<T> {
    public T data;
    public boolean success;
    public Error[] errors;

    public Request(ErrorCode... args) {

        success = false;
        data = null;
        errors = new Error[args.length];
        for (int i = 0; i < args.length; i++) {
            errors[i] = new Error(args[i]);
        }
    }

    public Request(T data, boolean success) {
        this.success = success;
        this.data = data;
        errors = new Error[0];
    }

}