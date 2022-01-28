package com.tweetapp.application.exception;

public class ApplicationException extends RuntimeException {


    /**
     * Instantiates a new business exception.
     *
     * @param message the message
     * @param th the th
     */
    public ApplicationException(String message, Throwable th) {
        super(message, th);
    }

    /**
     * Instantiates a new business exception.
     *
     * @param message the message
     */
    public ApplicationException(String message) {
        super(message);
    }

}
