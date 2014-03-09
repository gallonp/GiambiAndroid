package com.example.giambi.util;

/**
 * @author cwl
 */
public class GetAccountException extends Exception {

    /**
     * Serial Number generated automatically.
     */
    private static final long serialVersionUID = -6065780988761444840L;

    /**
     *
     */
    public GetAccountException() {
        super();
    }

    /**
     * @param detailMessage
     */
    public GetAccountException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * @param throwable
     */
    public GetAccountException(Throwable throwable) {
        super(throwable);
    }

    /**
     * @param detailMessage
     * @param throwable
     */
    public GetAccountException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
