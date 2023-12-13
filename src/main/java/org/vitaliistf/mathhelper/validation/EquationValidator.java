package org.vitaliistf.mathhelper.validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Utility class for validating mathematical equations.
 */
public class EquationValidator {

    private static final Set<String> OPERATORS = new HashSet<>(
            Arrays.asList("+", "*", "/", "=")
    );

    private static final Set<String> ALLOWED_SYMBOLS = new HashSet<>(
            Arrays.asList("+", "*", "/", "=", "-", "(", ")", ".", "x")
    );

    /**
     * Validates the given mathematical equation.
     *
     * @param equation The mathematical equation to validate.
     * @return True if the equation is valid, false otherwise.
     */
    public static boolean isValid(String equation) {
        String[] tokens = tokenizeEquation(equation);
        String lastToken = "=";
        Stack<String> parentheses = new Stack<>();

        for (String currentToken : tokens) {
            if (currentToken.equals(" ")) {
                continue;
            }
            if (!isValidToken(currentToken)) {
                return false;
            }

            if (currentToken.equals("(")) {
                parentheses.push(currentToken);
            } else if (currentToken.equals(")") && parentheses.isEmpty()) {
                return false;
            } else if (currentToken.equals(")")) {
                parentheses.pop();
            } else if (isNotValidOperatorSequence(lastToken, currentToken)
                    || isNotValidMVariableSequence(lastToken, currentToken)
                    || isNotValidMVariableSequence(currentToken, lastToken)) {
                return false;
            } else if (isValidNumericSequence(lastToken, currentToken)) {
                lastToken += currentToken;
            } else {
                lastToken = currentToken;
            }

        }
        return parentheses.isEmpty();
    }

    private static boolean isValidNumericSequence(String lastToken, String currentToken) {
        return isNumeric(currentToken) && isNumeric(lastToken);
    }

    private static boolean isNotValidMVariableSequence(String token1, String token2) {
        return !OPERATORS.contains(token1) && "x".equals(token2) && !"-".equals(token1);
    }

    private static boolean isNotValidOperatorSequence(String lastToken, String currentToken) {
        return OPERATORS.contains(lastToken) && OPERATORS.contains(currentToken);
    }

    private static String[] tokenizeEquation(String equation) {
        return equation.split("");
    }

    private static boolean isValidToken(String token) {
        return ALLOWED_SYMBOLS.contains(token) || isNumeric(token);
    }

    private static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return str.length() > 1 && str.charAt(0) == '-' && isNumeric(str.substring(1));
        }
    }
}
