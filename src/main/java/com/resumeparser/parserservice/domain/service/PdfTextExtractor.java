package com.resumeparser.parserservice.domain.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for extracting text from PDF files.
 * This interface defines methods for extracting text from both local PDF files
 * and PDF files uploaded as multipart files.
 */
public interface PdfTextExtractor {
    /**
     * Extracts text from a PDF file located at the specified file path.
     *
     * @param filePath the path to the PDF file from which text should be extracted
     * @return the extracted text as a String
     * @throws IllegalArgumentException if the file is not found or cannot be processed
     */
    String extractTextFromPdfFile(String filePath);

    /**
     * Extracts text from a PDF file uploaded as a multipart file.
     *
     * @param file the PDF file uploaded by the user
     * @return the extracted text as a String
     * @throws IllegalArgumentException if the file is invalid or cannot be processed
     */
    String extractTextFromMultipartFile(MultipartFile file);
}
