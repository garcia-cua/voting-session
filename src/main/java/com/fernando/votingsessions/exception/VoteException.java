package com.fernando.votingsessions.exception;

public class VoteException extends Exception {

    private static final long serialVersionUID = -5783907981412002235L;

    private final String errorCode;

    public VoteException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
