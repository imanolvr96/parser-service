package com.resumeparser.parserservice.infrastructure.adapter;

import com.resumeparser.parserservice.domain.service.PdfTextExtractor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class PdfTextExtractorImpl implements PdfTextExtractor {

    @Override
    public String extractTextFromPdf(String filePath) {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            String errorMessage = "File not found: " + filePath;
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        try (PDDocument document = Loader.loadPDF(path.toFile())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (IOException e) {
            String errorMessage = "Error extracting text from PDF file: " + filePath;
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }
}