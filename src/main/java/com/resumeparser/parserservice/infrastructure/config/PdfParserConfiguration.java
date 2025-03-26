package com.resumeparser.parserservice.infrastructure.config;

import com.resumeparser.parserservice.application.service.PdfParserService;
import com.resumeparser.parserservice.domain.service.PdfTextExtractor;
import com.resumeparser.parserservice.infrastructure.adapter.PdfTextExtractorImpl;
import com.resumeparser.parserservice.infrastructure.client.NlpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class responsible for setting up the beans required for PDF text extraction and processing.
 * <p>
 * This configuration defines the following beans:
 * - PdfTextExtractor: Bean for extracting text from PDF files.
 * - PdfParserService: Bean for processing extracted text with NLP and other logic.
 * <p>
 * The PdfParserService depends on PdfTextExtractor for text extraction and NlpClient for processing the extracted text.
 */
@Configuration
public class PdfParserConfiguration {

    /**
     * Bean for PdfTextExtractor which is responsible for extracting text from PDF files.
     *
     * @return PdfTextExtractor instance for text extraction from PDF files.
     */
    @Bean
    public PdfTextExtractor pdfExtractor() {
        return new PdfTextExtractorImpl();
    }

    /**
     * Bean for PdfParserService that handles the PDF text extraction and NLP processing.
     *
     * @param pdfExtractor the PdfTextExtractor used for extracting text from PDF files
     * @param nlpClient    the NlpClient used for processing the extracted text
     * @return PdfParserService instance for extracting and processing text from PDF files
     */
    @Bean
    public PdfParserService pdfService(PdfTextExtractor pdfExtractor, NlpClient nlpClient) {
        return new PdfParserService(pdfExtractor, nlpClient);
    }
}