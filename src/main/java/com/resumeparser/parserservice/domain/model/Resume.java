package com.resumeparser.parserservice.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record Resume(UUID id, byte[] pdfContent) {
    public Resume(byte[] pdfContent) {
        this(UUID.randomUUID(), pdfContent.clone());
    }
}