package com.resumeparser.parserservice.domain.model;

import lombok.Builder;

import java.util.UUID;

/**
 * Represents a Resume, containing a unique identifier and the PDF content.
 * This record class is used to encapsulate the resume data, including the PDF content.
 * It is intended to be used as a data transfer object for resumes.
 */
@Builder
public record Resume(UUID id, byte[] pdfContent) {
    /**
     * Constructor to create a Resume instance with a generated UUID.
     * The PDF content is cloned to ensure immutability of the byte array.
     *
     * @param pdfContent the content of the resume in PDF format
     */
    public Resume(byte[] pdfContent) {
        this(UUID.randomUUID(), pdfContent.clone());
    }
}