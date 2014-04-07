package com.example.giambi.util;

/**
 * Invalid argument exceptions. 
 * @author haolidu
 *
 */
public class InvalidArguementException extends Exception {

    /**
     * Constructor.
     */
    public InvalidArguementException() {
        super();
    }

    public InvalidArguementException(String detailMessage) {
        super(detailMessage);
    }

    public InvalidArguementException(Throwable throwable) {
        super(throwable);
    }

    public InvalidArguementException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
