package com.synk.models.data;

public class Response<T> {
    public T data;
    public boolean success;
    public Error[] errors;

    public Response(ErrorCode... args) {

        success = false;
        data = null;
        errors = new Error[args.length];
        for (int i = 0; i < args.length; i++) {
            errors[i] = new Error(args[i]);
        }
    }

    public Response(T data, boolean success) {
        this.success = success;
        this.data = data;
        errors = new Error[0];
    }

}