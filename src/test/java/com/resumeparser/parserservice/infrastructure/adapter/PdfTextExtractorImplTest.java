package com.resumeparser.parserservice.infrastructure.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PdfTextExtractorImplTest {
    private PdfTextExtractorImpl pdfTextExtractor;
    private Path testPdfPath;
    private Path validPdfPath;
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        pdfTextExtractor = new PdfTextExtractorImpl();
        testPdfPath = Path.of("src/main/resources/cv/sample.pdf");
        validPdfPath = Path.of("src/main/resources/cv/Image.jpeg");

        try {
            byte[] pdfContent = java.nio.file.Files.readAllBytes(testPdfPath);
            file = new MockMultipartFile("file", "sample.pdf", "application/pdf", pdfContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testExtractTextFromPdfFile_successfulExtraction() {
        String result = pdfTextExtractor.extractTextFromPdfFile(testPdfPath.toString());

        assertNotNull(result, "Extracted text should not be null");
    }

    @Test
    void testExtractTextFromMultipartFile_successfulExtraction() {
        String result = pdfTextExtractor.extractTextFromMultipartFile(file);

        assertNotNull(result, "Extracted text should not be null");
    }

    @Test
    void testExtractTextFromPdf_errorExtractingText() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pdfTextExtractor.extractTextFromPdfFile(validPdfPath.toString()));

        assertEquals("Error extracting text from PDF file: " + validPdfPath, exception.getMessage());
    }
}