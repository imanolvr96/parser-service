package com.resumeparser.parserservice.infrastructure.exception;

import lombok.Getter;

/**
 * Custom exception to handle errors that occur during NLP processing.
 * This exception is thrown when there is an issue while processing the resume
 * with the NLP service, such as service unavailability, unexpected responses,
 * or failure to process the text.
 */
@Getter
public class NlpProcessingException extends RuntimeException {
    private final String errorCode;
    private final String context;

    /**
     * Constructor to create a new NlpProcessingException with a message, cause, error code, and context.
     *
     * @param message   the error message to describe the exception
     * @param cause     the original exception that caused this exception
     * @param errorCode a specific error code to categorize the exception
     * @param context   additional context to provide more details about the error (e.g., file name or operation)
     */
    public NlpProcessingException(String message, Throwable cause, String errorCode, String context) {
        super(message, cause);
        this.errorCode = errorCode != null ? errorCode : "NLP_ERROR";
        this.context = context != null ? context : "Unknown context";
    }

    @Override
    public String toString() {
        return String.format("NlpProcessingException{message=%s, errorCode=%s, context=%s, cause=%s}",
                getMessage(), errorCode, context, getCause());
    }
}