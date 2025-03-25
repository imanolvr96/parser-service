package com.resumeparser.parserservice.application.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class FilePathValidatorTest {

    @Test
    void testValidate_ShouldThrowException_WhenFilePathIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            FilePathValidator.validateFilePath(null);
        });

        assertEquals("File path cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testValidate_ShouldThrowException_WhenFilePathIsEmpty() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            FilePathValidator.validateFilePath(" ");
        });

        assertEquals("File path cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testValidate_ShouldNotThrowException_WhenFilePathIsValid() {
        assertDoesNotThrow(() -> {
            FilePathValidator.validateFilePath("/valid/path/to/file");
        });
    }
}
