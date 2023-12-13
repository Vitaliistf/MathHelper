package org.vitaliistf.mathhelper.validation;

import java.util.Optional;

/**
 * Utility class for converting a string to a Double value.
 */
public class RootConverter {

    /**
     * Converts the given string to a Double value, if possible.
     *
     * @param n The string to convert.
     * @return An Optional containing the Double value if conversion is successful, or empty if unsuccessful.
     */
    public static Optional<Double> convert(String n) {
        if (n == null) {
            return Optional.empty();
        }
        try {
           return Optional.of(Double.valueOf(n));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

}
