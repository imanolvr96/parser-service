package com.resumeparser.parserservice.infrastructure.exception;

import com.resumeparser.parserservice.application.service.PdfParserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private PdfParserService pdfParserService;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void testHandleFileNotFoundException() {
        FileNotFoundException ex = new FileNotFoundException("Simulated file not found exception");

        ResponseEntity<String> response = globalExceptionHandler.handleFileNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("File not found: Simulated file not found exception", response.getBody());
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException exception = new RuntimeException("Runtime error occurred");

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<String> response = handler.handleRuntimeException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Runtime error occurred", response.getBody());
    }

    @Test
    void testHandleIOException() {
        IOException exception = new IOException("File error");

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<String> response = handler.handleIOException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("File error: File error", response.getBody());
    }

    @Test
    void testHandleNlpProcessingException() {
        NlpProcessingException exception = new NlpProcessingException("NLP processing failed", new RuntimeException(), "NLP_ERROR", "test.pdf");

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<Object> response = handler.handleNlpProcessingException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        Object body = response.getBody();
        
        assertNotNull(body);
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Unexpected error");

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<String> response = handler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error: Unexpected error", response.getBody());
    }
}