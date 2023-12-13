package org.vitaliistf.mathhelper.evaluation;

/**
 * Utility class for evaluating mathematical equations.
 */
public class EquationEvaluator {

    public static double PRECISION = 1e-10;

    /**
     * Evaluates the given mathematical equation by substituting the specified value for 'x'.
     *
     * @param equation The mathematical equation in the form "leftExpression = rightExpression".
     * @param x        The value to substitute for 'x'.
     * @return True if the equation is satisfied, false otherwise.
     */
    public static boolean evaluate(String equation, double x) {
        equation = equation.replaceAll("x", String.valueOf(x));
        String[] expressions = equation.split("=");
        double leftPart = evaluateExpression(expressions[0], x);
        double rightPart = evaluateExpression(expressions[1], x);
        return Math.abs(leftPart - rightPart) < PRECISION;
    }

    /**
     * Evaluates a mathematical expression by substituting the specified value for 'x'.
     *
     * @param expression The mathematical expression to evaluate.
     * @param x          The value to substitute for 'x'.
     * @return The result of the expression evaluation.
     */
    private static double evaluateExpression(String expression, double x) {
        expression = expression.replaceAll("x", String.valueOf(x));
        return new StatefulEvaluationHelper(expression).parse();
    }

}

