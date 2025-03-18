package com.resumeparser.parserservice.infrastructure.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PdfTextExtractorImplTest {
    private PdfTextExtractorImpl pdfTextExtractor;
    private Path testPdfPath;
    private Path validPdfPath;
    private Path nonExistentFile;

    @BeforeEach
    void setUp() {
        pdfTextExtractor = new PdfTextExtractorImpl();
        testPdfPath = Path.of("src/main/resources/cv/sample.pdf");
        validPdfPath = Path.of("src/main/resources/cv/Image.jpeg");
        nonExistentFile = Path.of("src/test/resources/non_existent_file.pdf");
    }

    @Test
    void testExtractTextFromPdf_fileNotFound() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pdfTextExtractor.extractTextFromPdf(nonExistentFile.toString());
        });
        assertEquals("File not found: " + nonExistentFile.toString(), exception.getMessage());
    }

    @Test
    void testExtractTextFromPdf_successfulExtraction() {
        String result = pdfTextExtractor.extractTextFromPdf(testPdfPath.toString());

        assertNotNull(result, "Extracted text should not be null");
    }

    @Test
    void testExtractTextFromPdf_errorExtractingText() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pdfTextExtractor.extractTextFromPdf(validPdfPath.toString());
        });

        assertEquals("Error extracting text from PDF file: " + validPdfPath, exception.getMessage());
    }
}