package com.synk.models.data;

public class Error {
    public String message;
    public ErrorCode code;

    public Error(ErrorCode arg) {
        this.code = arg;
        switch (arg) {
            case ARG1:
                this.message = "Argument 1 is invalid";
                break;
            case ARG2:
                this.message = "Argument 2 is invalid";
                break;
            case ARG3:
                this.message = "Argument 3 is invalid";
                break;
            case ARG4:
                this.message = "Argument 4 is invalid";
                break;
            case ARG5:
                this.message = "Argument 5 is invalid";
                break;
            case ARG6:
                this.message = "Argument 6 is invalid";
                break;
            case ARG7:
                this.message = "Argument 7 is invalid";
                break;
            case ARG8:
                this.message = "Argument 8 is invalid";
                break;
            case EXISTS:
                this.message = "Already exists";
                break;
            case NOT_EXISTS:
                this.message = "Does not exist";
                break;
            case UNAUTHORIZED:
                this.message = "Unauthorized";
                break;
            default:
                this.message = "Unknown error";
                break;
        }
    }
}
