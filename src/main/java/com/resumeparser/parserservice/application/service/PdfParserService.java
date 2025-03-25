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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class PdfParserService {

    private final PdfTextExtractor pdfTextExtractor;
    private final NlpClient nlpClient;

    /**
     * Constructor for PdfParserService.
     *
     * @param pdfTextExtractor the service used to extract text from PDFs
     * @param nlpClient        the client used for NLP processing
     */
    public PdfParserService(PdfTextExtractor pdfTextExtractor, NlpClient nlpClient) {
        this.pdfTextExtractor = pdfTextExtractor;
        this.nlpClient = nlpClient;
    }

    /**
     * Extracts text from a PDF file located at the given file path and processes it
     * using the NLP service.
     *
     * @param filePath the path to the PDF file
     * @return the processed resume object after NLP analysis
     */
    public Resume extractTextFromPdfFile(String filePath) {
        FilePathValidator.validateFilePath(filePath);
        log.info("Starting text extraction from PDF located at: {}", filePath);
        String extractedText = pdfTextExtractor.extractTextFromPdfFile(filePath);
        log.info("Successfully extracted text from PDF: {}", filePath);
        return processWithNlpService(extractedText, filePath);
    }

    /**
     * Extracts text from a PDF file uploaded as a MultipartFile and processes it
     * using the NLP service.
     *
     * @param file the PDF file uploaded by the user
     * @return the processed resume object after NLP analysis
     */
    public Resume extractTextFromMultipartFile(MultipartFile file) {
        FilePathValidator.validateMultipartFile(file);
        log.info("Starting text extraction from uploaded PDF: {}", file.getOriginalFilename());
        String extractedText = pdfTextExtractor.extractTextFromMultipartFile(file);
        log.info("Successfully extracted text from uploaded PDF: {}", file.getOriginalFilename());
        return processWithNlpService(extractedText, file.getOriginalFilename());
    }

    /**
     * Sends the extracted text to the NLP service for processing and returns
     * the processed resume object.
     *
     * @param extractedText the text extracted from the PDF file
     * @param fileName      the name of the PDF file for logging and error context
     * @return the processed resume object after NLP analysis
     */
    private Resume processWithNlpService(String extractedText, String fileName) {
        log.info("Text extraction completed. Sending extracted text to NLP service for processing.");

        try {
            Resume resume = nlpClient.processResume(extractedText);
            log.info("NLP processing completed successfully for file: {}", fileName);

            ResumeValidator.validate(resume);
            log.info("Resume validated successfully for file: {}", fileName);

            return resume;
        } catch (FeignException e) {
            log.error("Error during NLP processing for file: {}", fileName, e);
            throw new NlpProcessingException("Failed to process the text with the NLP service", e,
                    "NLP_SERVICE_ERROR", "File: " + fileName + " | NLP Service failure");
        } catch (Exception e) {
            log.error("Unexpected error occurred while processing the resume for file: {}", fileName, e);
            throw new RuntimeException("Unexpected error occurred during NLP processing", e);
        }
    }
}