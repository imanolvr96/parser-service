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
     * Endpoint to extract content from a given PDF file.
     *
     * @param filePath the path to the PDF file
     * @return ResponseEntity with the extracted text
     */
    @PostMapping("/extract")
    public ResponseEntity<Resume> extractPdfContent(@RequestParam String filePath) {
        log.info("Received request to extract content from PDF: {}", filePath);
        Resume extractedText = pdfParserService.extractTextFromPdf(filePath);
        log.info("Successfully extracted text from PDF: {}", filePath);
        return ResponseEntity.ok(extractedText);
    }
}