package com.resumeparser.parserservice.infrastructure.controller;

import com.resumeparser.parserservice.application.service.PdfParserService;
import com.resumeparser.parserservice.domain.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PdfControllerTest {

    @Mock
    private PdfParserService pdfParserService;

    @InjectMocks
    private PdfController pdfController;

    private String filePath;

    @BeforeEach
    void setUp() {
        filePath = "path/to/test.pdf";
    }

    @Test
    void testExtractPdfContent_Success() {
        byte[] mockPdfContent = "Extracted text from PDF".getBytes();
        Resume mockResume = new Resume(mockPdfContent);

        when(pdfParserService.extractTextFromPdf(filePath)).thenReturn(mockResume);

        ResponseEntity<Resume> response = pdfController.extractPdfContent(filePath);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertArrayEquals(mockPdfContent, response.getBody().pdfContent());
    }
}
