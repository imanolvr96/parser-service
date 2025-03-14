package com.resumeparser.parserservice.infrastructure.config;

import com.resumeparser.parserservice.application.service.PdfParserService;
import com.resumeparser.parserservice.domain.service.PdfTextExtractor;
import com.resumeparser.parserservice.infrastructure.adapter.PdfBoxPdfExtractor;
import com.resumeparser.parserservice.infrastructure.client.NlpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PdfParserConfiguration {

    @Bean
    public PdfTextExtractor pdfExtractor() {
        return new PdfBoxPdfExtractor();
    }

    @Bean
    public PdfParserService pdfService(PdfTextExtractor pdfExtractor, NlpClient nlpClient) {
        return new PdfParserService(pdfExtractor, nlpClient);
    }
}