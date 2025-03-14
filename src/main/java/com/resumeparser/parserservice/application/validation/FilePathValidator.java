package com.resumeparser.parserservice.application.validation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilePathValidator {

    public static void validate(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            log.warn("Invalid file path provided: {}", filePath);
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
    }
}