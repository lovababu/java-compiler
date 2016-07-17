package org.avol.jcompiler.security.exception;

import lombok.Getter;

/**
 * Created by Durga on 7/12/2016.
 */
@Getter
public class SecurityException extends Exception {

    private int statusCode;
    private String message;

    public SecurityException() {
        super();
    }

    public SecurityException(String message) {
        super(message);
        this.message = message;
    }

    public SecurityException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }
}
