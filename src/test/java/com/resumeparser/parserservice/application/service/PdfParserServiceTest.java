package com.resumeparser.parserservice.application.service;

import com.resumeparser.parserservice.application.validation.FilePathValidator;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PdfParserServiceTest {

    @Mock
    private PdfTextExtractor pdfTextExtractor;

    @Mock
    private NlpClient nlpClient;

    private PdfParserService pdfParserService;

    @BeforeEach
    void setUp() {
        pdfParserService = new PdfParserService(pdfTextExtractor, nlpClient);
    }

    @Test
    void testExtractTextFromPdf_ShouldReturnResume_WhenProcessingSucceeds() {
        String filePath = "/valid/path/to/pdf";
        String extractedText = "extracted text from pdf";
        Resume mockResume = new Resume("Extracted text from PDF".getBytes());

        when(pdfTextExtractor.extractTextFromPdf(filePath)).thenReturn(extractedText);
        when(nlpClient.processResume(extractedText)).thenReturn(mockResume);

        Resume result = pdfParserService.extractTextFromPdf(filePath);

        assertNotNull(result);
        assertEquals(mockResume, result);
    }

    @Test
    void testExtractTextFromPdf_ShouldThrowException_WhenNlpClientFails() {
        String filePath = "/valid/path/to/pdf";
        String extractedText = "extracted text from pdf";

        when(pdfTextExtractor.extractTextFromPdf(filePath)).thenReturn(extractedText);
        when(nlpClient.processResume(extractedText)).thenThrow(FeignException.class);

        NlpProcessingException thrown = assertThrows(NlpProcessingException.class, () -> pdfParserService.extractTextFromPdf(filePath));

        assertEquals("Failed to process the text with the NLP service", thrown.getMessage());
    }

    @Test
    void testExtractTextFromPdf_ShouldCallFilePathValidator_WhenFilePathIsValid() {
        String filePath = "/valid/path/to/pdf";

        FilePathValidator.validate(filePath);

        FilePathValidator.validate(filePath);
    }
}
