package com.a3wcm.exception;

public class EventException extends RuntimeException {

    public EventException() { super(); }

    public EventException(String message) { super(message); }

    public EventException(String message, Throwable cause) { super(message, cause); }

    public EventException(Throwable cause) {
        super(cause);
    }


}
