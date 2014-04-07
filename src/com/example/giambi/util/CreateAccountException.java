package com.example.giambi.util;

/**
 * @author cwl
 */
public class CreateAccountException extends Exception {

    /**
     * Serial Number generated automatically.
     */
    private static final long serialVersionUID = 3046205721180454710L;

    /**
     *
     */
    public CreateAccountException() {
        super();
    }

    /**
     * * this is created account exception
     * @param detailMessage this is detailed message
     */
    public CreateAccountException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * @param throwable
     */
    public CreateAccountException(Throwable throwable) {
        super(throwable);
    }

    /**
     * @param detailMessage
     * @param throwable
     */
    public CreateAccountException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
