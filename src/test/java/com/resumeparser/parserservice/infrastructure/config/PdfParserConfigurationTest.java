package com.resumeparser.parserservice.infrastructure.config;

import com.resumeparser.parserservice.application.service.PdfParserService;
import com.resumeparser.parserservice.domain.service.PdfTextExtractor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PdfParserConfigurationTest {

    @Autowired
    private PdfParserService pdfParserService;

    @Autowired
    private PdfTextExtractor pdfTextExtractor;

    @Test
    void testPdfParserServiceBean() {
        assertNotNull(pdfParserService, "PdfParserService should not be null");
    }

    @Test
    void testPdfExtractorBean() {
        assertNotNull(pdfTextExtractor, "PdfTextExtractor should not be null");
        assertNotNull(pdfTextExtractor, "PdfTextExtractor bean should not be null");
    }
}