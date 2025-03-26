package com.resumeparser.parserservice.infrastructure.adapter;

import com.resumeparser.parserservice.domain.service.PdfTextExtractor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Implementation of the PdfTextExtractor interface for extracting text from PDF files.
 * <p>
 * This class provides functionality to extract text from PDFs in two ways:
 * 1. From a file path (using the file system).
 * 2. From an uploaded MultipartFile (used in HTTP requests).
 * <p>
 * The extraction process is handled by using Apache PDFBox to load and parse the PDF content,
 * and PDFTextStripper is used to extract the text. Common logic for text extraction is centralized
 * in a private method to avoid code duplication.
 * <p>
 * The following methods are available:
 * - extractTextFromPdfFile: Extracts text from a file given its file path.
 * - extractTextFromMultipartFile: Extracts text from a file uploaded as a MultipartFile.
 * <p>
 * The implementation also handles errors during the extraction process and logs meaningful messages
 * to aid debugging.
 *
 * @see PdfTextExtractor
 */
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