package com.synk.models.data;

public class Error {
    public String message;
    public ErrorCode code;

    public Error(ErrorCode arg) {
        this.code = arg;
        switch (arg) {
            case INVALID_PASSWORD -> this.message = "Invalid password";
            case INVALID_SESSION -> this.message = "Invalid session";
            case INVALID_EMAIL -> this.message = "Invalid email";
            case INVALID_NAME -> this.message = "Invalid name";
            case EXISTS -> this.message = "Already exists";
            case NOT_EXISTS -> this.message = "Does not exist";
            case UNAUTHORIZED -> this.message = "Unauthorized for action";
            case ARGUMENT_NULL -> this.message = "Parameter was null";
            case UNKNOWN -> this.message = "Unknown error";
        }
    }
}
