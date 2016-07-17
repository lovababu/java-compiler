package org.avol.jcompiler.compile.exception;

import lombok.Getter;

/**
 * Created by Durga on 7/12/2016.
 */
@Getter
public class CompileException extends Exception{
    private String message;
    private int statusCode;

    public CompileException() {
    }

    public CompileException(String message) {
        this.message = message;
    }

    public CompileException(int statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
