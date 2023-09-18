package com.synk.models.data;

public class Error {
    public String message;
    public ErrorCode code;

    public Error(ErrorCode arg) {
        this.code = arg;
        switch (arg) {
            case INVALID_PASSWORD:
                this.message = "Invalid password. Must be 8 characters";
                break;
            case INVALID_EMAIL:
                this.message = "Invalid email. Should follow the regular format";
                break;
            case EXISTS:
                this.message = "Already exists. Could not instantiate new";
                break;
            case NOT_EXISTS:
                this.message = "Does not exist. Could not find";
                break;
            case UNAUTHORIZED:
                this.message = "Unauthorized";
                break;
            case INVALID_SESSION:
                this.message = "Session has expired";
                break;
            case UNKNOWN:
                this.message = "Unknown error occurred";
                break;
        }
    }
}
