package com.resumeparser.parserservice.domain.validation;

import com.resumeparser.parserservice.domain.model.Resume;
import lombok.extern.slf4j.Slf4j;

/**
 * The {@link ResumeValidator} class provides a utility method to validate a {@link Resume} object.
 * <p>
 * This class is designed to ensure that the resume object passed to it is not null. If the object is null,
 * a warning is logged, and a {@link RuntimeException} is thrown to signal that the resume parsing failed.
 * </p>
 * <p>
 * This class helps ensure the integrity of the data being processed by the application, preventing
 * further operations on invalid or incomplete resume data.
 * </p>
 */
@Slf4j
public class ResumeValidator {

    /**
     * Validates the given Resume object.
     * <p>
     * This method checks if the Resume object is null and throws a RuntimeException
     * with an appropriate error message if the validation fails.
     * </p>
     *
     * @param resume the Resume object to be validated
     * @throws RuntimeException if the Resume object is null
     */
    public static void validate(Resume resume) {
        if (resume == null) {
            log.warn("Resume parsing failed. Received null response.");
            throw new RuntimeException("Resume parsing failed. Received null response.");
        }
    }
}