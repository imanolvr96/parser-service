package com.resumeparser.parserservice.infrastructure.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NlpProcessingExceptionTest {

    @Test
    void testNlpProcessingException_ConstructorAndToString() {
        String message = "NLP processing failed";
        Throwable cause = new RuntimeException("Original exception");
        String errorCode = "NLP_ERROR";
        String context = "test.pdf";

        NlpProcessingException exception = new NlpProcessingException(message, cause, errorCode, context);

        assertEquals(message, exception.getMessage());
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(context, exception.getContext());
        assertEquals(cause, exception.getCause());

        String expectedToString = String.format(
                "NlpProcessingException{message=%s, errorCode=%s, context=%s, cause=%s}",
                message, errorCode, context, cause);
        assertEquals(expectedToString, exception.toString());
    }

    @Test
    void testNlpProcessingException_DefaultErrorCodeAndContext() {
        String message = "NLP processing failed";
        Throwable cause = new RuntimeException("Original exception");

        NlpProcessingException exception = new NlpProcessingException(message, cause, null, null);

        assertEquals(message, exception.getMessage());
        assertEquals("NLP_ERROR", exception.getErrorCode());
        assertEquals("Unknown context", exception.getContext());
        assertEquals(cause, exception.getCause());
        
        String expectedToString = String.format(
                "NlpProcessingException{message=%s, errorCode=%s, context=%s, cause=%s}",
                message, "NLP_ERROR", "Unknown context", cause);
        assertEquals(expectedToString, exception.toString());
    }
}