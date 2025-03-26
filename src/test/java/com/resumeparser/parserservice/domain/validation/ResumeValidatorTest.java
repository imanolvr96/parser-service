package com.resumeparser.parserservice.domain.validation;

import com.resumeparser.parserservice.domain.model.Resume;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ResumeValidatorTest {

    @Test
    void testValidate_ShouldThrowException_WhenResumeIsNull() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> ResumeValidator.validate(null));

        assertEquals("Resume parsing failed. Received null response.", thrown.getMessage());
    }

    @Test
    void testValidate_ShouldNotThrowException_WhenResumeIsNotNull() {
        assertDoesNotThrow(() -> ResumeValidator.validate(new Resume("Extracted text from PDF".getBytes())));
    }
}