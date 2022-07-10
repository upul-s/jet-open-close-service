package com.jet.restaurants.service.openclose.exception;

import lombok.Getter;

import static java.text.MessageFormat.format;

@Getter
public class ConnectException extends RuntimeException{
    private static final long serialVersionUID = -1818719426243364586L;

    private final String key;
    private final String message;

    public ConnectException(Exception e, String key, String message, Object... msgVars) {
        super(e);
        this.key = key;
        this.message = format(message, msgVars);
    }

    public ConnectException(String key, String message, Object... msgVars) {
        this.key = key;
        this.message = format(message, msgVars);
    }
}
