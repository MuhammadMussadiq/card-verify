package com.assignment.card.exception;

/**
 * Created by Mmussadiq on 4/5/2019.
 */
public class NotFoundException extends RuntimeException {

    private int code;

    private String message;

    public NotFoundException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
