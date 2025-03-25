package com.resumeparser.parserservice.infrastructure.adapter;

import com.resumeparser.parserservice.domain.service.PdfTextExtractor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
public class PdfTextExtractorImpl implements PdfTextExtractor {

    /**
     * Extracts text from a PDF file given its file path.
     *
     * @param filePath the path to the PDF file
     * @return extracted text from the PDF
     */
    @Override
    public String extractTextFromPdfFile(String filePath) {
        log.info("Starting text extraction from PDF file at path: {}", filePath);
        return extractText(() -> Loader.loadPDF(Paths.get(filePath).toFile()), filePath);
    }

    /**
     * Extracts text from a PDF file uploaded as a MultipartFile.
     *
     * @param file the uploaded PDF file
     * @return extracted text from the PDF
     */
    @Override
    public String extractTextFromMultipartFile(MultipartFile file) {
        log.info("Starting text extraction from uploaded PDF file: {}", file.getOriginalFilename());
        return extractText(() -> Loader.loadPDF(file.getInputStream().readAllBytes()), file.getOriginalFilename());
    }

    /**
     * Common method for extracting text from a PDDocument.
     *
     * @param pdfLoader the function that loads the PDF document (either from file or MultipartFile)
     * @param fileName  the name of the file for logging and error context
     * @return extracted text from the PDF
     */
    private String extractText(PDFLoader pdfLoader, String fileName) {
        try (PDDocument document = pdfLoader.load()) {
            log.debug("Extracting text from PDDocument...");
            PDFTextStripper stripper = new PDFTextStripper();
            String extractedText = stripper.getText(document);
            log.info("Text extraction completed successfully for file: {}", fileName);
            return extractedText;
        } catch (IOException e) {
            String errorMessage = "Error extracting text from PDF file: " + fileName;
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    /**
     * Functional interface to load PDF document from different sources.
     */
    @FunctionalInterface
    interface PDFLoader {
        PDDocument load() throws IOException;
    }
}