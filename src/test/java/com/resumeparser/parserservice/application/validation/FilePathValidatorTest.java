package com.resumeparser.parserservice.application.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class FilePathValidatorTest {

    @Test
    void testValidateFilePathNull() {
        Executable executable = () -> FilePathValidator.validateFilePath(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("File path cannot be null or empty", exception.getMessage());
    }

    @Test
    void testValidateFilePathEmpty() {
        Executable executable = () -> FilePathValidator.validateFilePath("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("File path cannot be null or empty", exception.getMessage());
    }

    @Test
    void testValidateFilePathValid() {
        String validFilePath = "path/to/valid/file.pdf";

        assertDoesNotThrow(() -> FilePathValidator.validateFilePath(validFilePath));
    }

    @Test
    void testValidateMultipartFileNull() {
        Executable executable = () -> FilePathValidator.validateMultipartFile(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("File cannot be null.", exception.getMessage());
    }

    @Test
    void testValidateMultipartFileEmpty() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "empty.pdf", "application/pdf", new byte[0]);

        Executable executable = () -> FilePathValidator.validateMultipartFile(emptyFile);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("File cannot be empty.", exception.getMessage());
    }

    @Test
    void testValidateMultipartFileInvalidExtension() {
        MockMultipartFile invalidFile = new MockMultipartFile("file", "invalid.txt", "text/plain", "Invalid content".getBytes());

        Executable executable = () -> FilePathValidator.validateMultipartFile(invalidFile);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Only PDF files are allowed.", exception.getMessage());
    }

    @Test
    void testValidateMultipartFileValid() {
        MockMultipartFile validFile = new MockMultipartFile("file", "valid.pdf", "application/pdf", "Valid content".getBytes());

        assertDoesNotThrow(() -> FilePathValidator.validateMultipartFile(validFile));
    }

    @Test
    void testValidateMultipartFileNullFilename() {
        MockMultipartFile invalidFile = new MockMultipartFile("file", null, "application/pdf", "Valid content".getBytes());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> FilePathValidator.validateMultipartFile(invalidFile));

        assertEquals("Only PDF files are allowed.", exception.getMessage());
    }
}
