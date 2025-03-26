package com.resumeparser.parserservice.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for managing different types of exceptions thrown during the processing
 * of PDF extraction, NLP processing, and file handling.
 * <p>
 * This class provides centralized exception handling for various specific exceptions that may occur,
 * ensuring appropriate responses are returned to the client. The handler includes logging and custom
 * error messages for better diagnostics and client feedback.
 * <p>
 * The following exceptions are handled:
 * - FileNotFoundException: When the file is not found in the specified path.
 * - RuntimeException: For unexpected runtime errors.
 * - IOException: For I/O issues such as file reading errors.
 * - NlpProcessingException: For errors related to the NLP processing service.
 * - Exception: For any unforeseen or generic errors.
 * - MultipartException: For issues with multipart file uploads.
 * - IllegalArgumentException: For invalid arguments passed to the methods.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles FileNotFoundException by returning a NOT_FOUND status and an error message.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with a 404 status and the error message
     */
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException ex) {
        log.warn("File not found: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: " + ex.getMessage());
    }

    /**
     * Handles RuntimeException by returning an INTERNAL_SERVER_ERROR status and a generic error message.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with a 500 status and the error message
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        log.error("RuntimeException occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }

    /**
     * Handles IOException by returning a BAD_REQUEST status and a file-related error message.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with a 400 status and the error message
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        log.warn("IOException occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File error: " + ex.getMessage());
    }

    /**
     * Handles NlpProcessingException by returning an INTERNAL_SERVER_ERROR status and a detailed response.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with a 500 status and a detailed error message with error code and context
     */
    @ExceptionHandler(NlpProcessingException.class)
    public ResponseEntity<Object> handleNlpProcessingException(NlpProcessingException ex) {
        log.error("NLP processing error: {}", ex.getMessage(), ex);

        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("errorCode", ex.getErrorCode());
        response.put("context", "Error during NLP processing for file: " + ex.getContext());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles any unexpected exceptions by returning an INTERNAL_SERVER_ERROR status and a generic error message.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with a 500 status and the error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
    }

    /**
     * Handles MultipartException by returning a BAD_REQUEST status and an upload-related error message.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with a 400 status and the error message
     */
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipartException(MultipartException ex) {
        log.warn("Multipart file upload error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File upload failed: " + ex.getMessage());
    }

    /**
     * Handles MultipartException by returning a BAD_REQUEST status and an upload-related error message.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with a 400 status and the error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Invalid argument: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}