package com.a3wcm.exception;

public class ConfigException extends RuntimeException {

    public ConfigException() { super(); }

    public ConfigException(String message) { super(message); }

    public ConfigException(String message, Throwable cause) { super(message, cause); }

    public ConfigException(Throwable cause) {
        super(cause);
    }
    
    
}
