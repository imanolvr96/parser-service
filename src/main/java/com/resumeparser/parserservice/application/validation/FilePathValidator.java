package com.resumeparser.parserservice.application.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * Utility class that provides methods to validate file paths and files (specifically PDF files).
 * Ensures that file paths are not null or empty and that uploaded files are not null, not empty,
 * and have a valid PDF extension.
 * <p>
 * The validation methods throw an IllegalArgumentException if the input does not meet the expected conditions.
 * This class is used to ensure proper file handling before processing PDF files in the application.
 * </p>
 */
@Slf4j
public class FilePathValidator {

    /**
     * Validates the given file path to ensure it is not null or empty.
     *
     * @param filePath the file path to validate
     * @throws IllegalArgumentException if the file path is null or empty
     */
    public static void validateFilePath(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            log.warn("Invalid file path provided: {}", filePath);
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        log.info("File path validated successfully: {}", filePath);
    }

    /**
     * Validates the given MultipartFile to ensure it is not null, not empty, and has a valid extension (PDF).
     *
     * @param file the MultipartFile to validate
     * @throws IllegalArgumentException if the file is null, empty, or has an invalid extension
     */
    public static void validateMultipartFile(MultipartFile file) {
        if (file == null) {
            log.warn("File is null.");
            throw new IllegalArgumentException("File cannot be null.");
        }

        if (file.isEmpty()) {
            log.warn("Uploaded file is empty: {}", file.getOriginalFilename());
            throw new IllegalArgumentException("File cannot be empty.");
        }

        String fileExtension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        if (!"pdf".equalsIgnoreCase(fileExtension)) {
            log.warn("Invalid file extension provided: {}", file.getOriginalFilename());
            throw new IllegalArgumentException("Only PDF files are allowed.");
        }

        log.info("File validated successfully: {}", file.getOriginalFilename());
    }

    /**
     * Gets the file extension from the given file name.
     *
     * @param filename the name of the file
     * @return the file extension (e.g., "pdf")
     */
    private static String getFileExtension(String filename) {
        if (!filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}