package com.resumeparser.parserservice.infrastructure.controller;

import com.resumeparser.parserservice.application.service.PdfParserService;
import com.resumeparser.parserservice.domain.model.Resume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/pdf")
public class PdfController {

    private final PdfParserService pdfParserService;

    @Autowired
    public PdfController(PdfParserService pdfParserService) {
        this.pdfParserService = pdfParserService;
    }

    /**
     * Endpoint to extract content from a given PDF file path and process it.
     *
     * @param filePath the path to the PDF file
     * @return ResponseEntity with the processed Resume object
     */
    @PostMapping("/extract-text-from-file")
    public ResponseEntity<Resume> extractTextFromPdfFile(@RequestParam String filePath) {
        log.info("Received request to extract text from PDF file: {}", filePath);
        Resume extractedText = pdfParserService.extractTextFromPdfFile(filePath);
        log.info("Successfully extracted text from PDF file: {}", filePath);
        return ResponseEntity.ok(extractedText);
    }

    /**
     * Endpoint to extract text content from an uploaded PDF file and process it.
     *
     * @param file the uploaded PDF file
     * @return ResponseEntity with the processed Resume object
     */
    @PostMapping("/extract-text-from-upload")
    public ResponseEntity<Resume> extractTextFromMultipartFile(@RequestParam("file") MultipartFile file) {
        log.info("Received request to extract text from uploaded PDF: {}", file.getOriginalFilename());
        Resume extractedText = pdfParserService.extractTextFromMultipartFile(file);
        log.info("Successfully extracted text from uploaded PDF: {}", file.getOriginalFilename());
        return ResponseEntity.ok(extractedText);
    }
}