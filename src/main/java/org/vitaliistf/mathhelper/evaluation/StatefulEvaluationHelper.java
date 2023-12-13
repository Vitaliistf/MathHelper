package org.vitaliistf.mathhelper.evaluation;

/**
 * A helper class for stateful mathematical expression parsing and evaluation.
 */
public class StatefulEvaluationHelper {

    private final String expression;
    private int pos = -1;
    private int ch;

    /**
     * Constructs a StatefulEvaluationHelper with the specified mathematical expression.
     *
     * @param expression The mathematical expression to parse and evaluate.
     */
    public StatefulEvaluationHelper(String expression) {
        this.expression = expression;
    }

    /**
     * Parses the mathematical expression and returns the result.
     *
     * @return The result of the expression evaluation.
     */
    public double parse() {
        nextChar();
        return parseExpression();
    }

    /**
     * Moves to the next character in the expression.
     */
    private void nextChar() {
        ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
    }

    /**
     * Consumes whitespace and checks if the current character matches the specified one.
     *
     * @param charToEat The character to check for.
     * @return True if the character matches, false otherwise.
     */
    private boolean eat(int charToEat) {
        while (ch == ' ') {
            nextChar();
        }
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    /**
     * Parses an expression and handles addition and subtraction operations.
     *
     * @return The result of the expression evaluation.
     */
    private double parseExpression() {
        double x = parseTerm();
        for (;;) {
            if (eat('+')) {
                x += parseTerm(); // addition
            } else if (eat('-')) {
                x -= parseTerm(); // subtraction
            } else {
                return x;
            }
        }
    }

    /**
     * Parses a term and handles multiplication and division operations.
     *
     * @return The result of the expression evaluation.
     */
    private double parseTerm() {
        double x = parseFactor();
        for (;;) {
            if(eat('*')) {
                x *= parseFactor(); // multiplication
            } else if (eat('/')) {
                x /= parseFactor(); // division
            } else {
                return x;
            }
        }
    }

    /**
     * Parses a factor, handling unary plus, unary minus, parentheses, and numbers.
     *
     * @return The result of the expression evaluation.
     */
    private double parseFactor() {
        if (eat('+')) {
            return parseFactor(); // unary plus
        }
        if (eat('-')) {
            return -parseFactor(); // unary minus
        }

        double x;
        int startPos = this.pos;
        if (eat('(')) {
            x = parseExpression();
            eat(')');
        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
            while ((ch >= '0' && ch <= '9') || ch == '.') {
                nextChar();
            }
            x = Double.parseDouble(expression.substring(startPos, this.pos));
        }  else {
            throw new RuntimeException("Unexpected: " + (char)ch);
        }

        return x;
    }

}
