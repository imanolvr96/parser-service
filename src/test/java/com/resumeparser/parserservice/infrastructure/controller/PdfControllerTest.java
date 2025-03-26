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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PdfControllerTest {

    @Mock
    private PdfParserService pdfParserService;

    @InjectMocks
    private PdfController pdfController;

    private String filePath;
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        filePath = "path/to/test.pdf";
        file = new MockMultipartFile("file", "test.pdf", "application/pdf", "Test PDF content".getBytes());
    }

    @Test
    void testExtractPdfContent_Success() {
        byte[] mockPdfContent = "Extracted text from PDF".getBytes();
        Resume mockResume = new Resume(mockPdfContent);

        when(pdfParserService.extractTextFromPdfFile(filePath)).thenReturn(mockResume);

        ResponseEntity<Resume> response = pdfController.extractTextFromPdfFile(filePath);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertArrayEquals(mockPdfContent, response.getBody().pdfContent());
    }

    @Test
    void testExtractTextFromMultipartFile() {
        byte[] mockPdfContent = "Extracted text from PDF".getBytes();
        Resume mockResume = new Resume(mockPdfContent);

        when(pdfParserService.extractTextFromMultipartFile(file)).thenReturn(mockResume);

        ResponseEntity<Resume> response = pdfController.extractTextFromMultipartFile(file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResume, response.getBody());
        verify(pdfParserService, times(1)).extractTextFromMultipartFile(file);
    }
}
