package com.resumeparser.parserservice.application.service;

import com.resumeparser.parserservice.application.validation.FilePathValidator;
import com.resumeparser.parserservice.domain.model.Resume;
import com.resumeparser.parserservice.domain.service.PdfTextExtractor;
import com.resumeparser.parserservice.domain.validation.ResumeValidator;
import com.resumeparser.parserservice.infrastructure.client.NlpClient;
import com.resumeparser.parserservice.infrastructure.exception.NlpProcessingException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PdfParserService {

    private final PdfTextExtractor pdfTextExtractor;
    private final NlpClient nlpClient;

    public PdfParserService(PdfTextExtractor pdfTextExtractor, NlpClient nlpClient) {
        this.pdfTextExtractor = pdfTextExtractor;
        this.nlpClient = nlpClient;
    }

    public Resume extractTextFromPdf(String filePath) {
        FilePathValidator.validate(filePath);

        log.info("Extracting text from PDF: {}", filePath);
        String extractedText = pdfTextExtractor.extractTextFromPdf(filePath);
        log.info("Text extraction completed. Sending text to NLP service.");

        try {
            Resume resume = nlpClient.processResume(extractedText);
            ResumeValidator.validate(resume);
            log.info("NLP processing completed successfully.");
            return resume;
        } catch (FeignException e) {
            log.error("Error during NLP processing for file: {}", filePath, e);
            throw new NlpProcessingException("Failed to process the text with the NLP service", e,
                    "NLP_SERVICE_ERROR", "File: " + filePath + " | NLP Service failure");
        }
    }
}