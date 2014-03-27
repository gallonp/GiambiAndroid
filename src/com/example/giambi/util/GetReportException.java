package com.example.giambi.util;

/**
 * @author cwl
 */
public class GetReportException extends Exception {

	/**
     *
     */
	public GetReportException() {
		super();
	}

	/**
	 * @param detailMessage
	 */
	public GetReportException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * @param throwable
	 */
	public GetReportException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * @param detailMessage
	 * @param throwable
	 */
	public GetReportException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
