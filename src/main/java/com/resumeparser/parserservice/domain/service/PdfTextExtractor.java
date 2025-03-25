package com.resumeparser.parserservice.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface PdfTextExtractor {
    String extractTextFromPdfFile(String filePath);

    String extractTextFromMultipartFile(MultipartFile file);
}
