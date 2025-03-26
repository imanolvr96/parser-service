package com.resumeparser.parserservice.application.service;

import com.resumeparser.parserservice.domain.model.Resume;
import com.resumeparser.parserservice.domain.service.PdfTextExtractor;
import com.resumeparser.parserservice.infrastructure.client.NlpClient;
import com.resumeparser.parserservice.infrastructure.exception.NlpProcessingException;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PdfParserServiceTest {

    private final String testFilePath = "src/test/resources/sample.pdf";
    private final String extractedText = "This is the extracted text from the PDF";
    @Mock
    private PdfTextExtractor pdfTextExtractor;
    @Mock
    private NlpClient nlpClient;
    private PdfParserService pdfParserService;
    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        pdfParserService = new PdfParserService(pdfTextExtractor, nlpClient);
        mockFile = new MockMultipartFile("file", "sample.pdf", "application/pdf", "PDF content".getBytes());
    }

    @Test
    void testExtractTextFromPdfFile_successfulExtraction() {
        when(pdfTextExtractor.extractTextFromPdfFile(testFilePath)).thenReturn(extractedText);

        Resume mockResume = new Resume("Extracted text from PDF".getBytes());
        when(nlpClient.processResume(extractedText)).thenReturn(mockResume);

        Resume result = pdfParserService.extractTextFromPdfFile(testFilePath);
        assertNotNull(result, "Processed resume should not be null");

        verify(pdfTextExtractor).extractTextFromPdfFile(testFilePath);
        verify(nlpClient).processResume(extractedText);
    }

    @Test
    void testExtractTextFromMultipartFile_successfulExtraction() {
        when(pdfTextExtractor.extractTextFromMultipartFile(mockFile)).thenReturn(extractedText);

        Resume mockResume = new Resume("Extracted text from PDF".getBytes());
        when(nlpClient.processResume(extractedText)).thenReturn(mockResume);

        Resume result = pdfParserService.extractTextFromMultipartFile(mockFile);
        assertNotNull(result, "Processed resume should not be null");

        verify(pdfTextExtractor).extractTextFromMultipartFile(mockFile);
        verify(nlpClient).processResume(extractedText);
    }


    @Test
    void testExtractTextFromPdfFile_unexpectedException() {
        when(pdfTextExtractor.extractTextFromPdfFile(testFilePath)).thenReturn(extractedText);

        when(nlpClient.processResume(extractedText)).thenThrow(new RuntimeException("Unexpected error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pdfParserService.extractTextFromPdfFile(testFilePath));
        assertEquals("Unexpected error occurred during NLP processing", exception.getMessage());
    }

    @Test
    void testExtractTextFromMultipartFile_unexpectedException() {
        when(pdfTextExtractor.extractTextFromMultipartFile(mockFile)).thenReturn(extractedText);

        when(nlpClient.processResume(extractedText)).thenThrow(new RuntimeException("Unexpected error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pdfParserService.extractTextFromMultipartFile(mockFile));
        assertEquals("Unexpected error occurred during NLP processing", exception.getMessage());
    }

    @Test
    void testExtractTextFromPdf_ShouldThrowException_WhenNlpClientFails() {
        String filePath = "/valid/path/to/pdf";
        String extractedText = "extracted text from pdf";

        when(pdfTextExtractor.extractTextFromPdfFile(filePath)).thenReturn(extractedText);
        when(nlpClient.processResume(extractedText)).thenThrow(FeignException.class);

        NlpProcessingException thrown = assertThrows(NlpProcessingException.class, () -> pdfParserService.extractTextFromPdfFile(filePath));

        assertEquals("Failed to process the text with the NLP service", thrown.getMessage());
    }
}