package org.vitaliistf.mathhelper.validation;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RootConverterTest {

    @Test
    void convertValidString() {
        String validString = "123.45";
        Optional<Double> result = RootConverter.convert(validString);
        assertTrue(result.isPresent());
        assertEquals(123.45, result.get(), 0.0001);
    }

    @Test
    void convertInvalidString() {
        String invalidString = "abc";
        Optional<Double> result = RootConverter.convert(invalidString);
        assertTrue(result.isEmpty());
    }

    @Test
    void convertNullString() {
        Optional<Double> result = RootConverter.convert(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void convertEmptyString() {
        String emptyString = "";
        Optional<Double> result = RootConverter.convert(emptyString);
        assertTrue(result.isEmpty());
    }
}

