package com.resumeparser.parserservice.domain.validation;

import com.resumeparser.parserservice.domain.model.Resume;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResumeValidator {

    public static void validate(Resume resume) {
        if (resume == null) {
            log.warn("Resume parsing failed. Received null response.");
            throw new RuntimeException("Resume parsing failed. Received null response.");
        }
    }
}