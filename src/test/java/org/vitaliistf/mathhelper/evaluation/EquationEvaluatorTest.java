package org.vitaliistf.mathhelper.evaluation;

import org.junit.jupiter.api.Test;
import org.vitaliistf.mathhelper.evaluation.EquationEvaluator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EquationEvaluatorTest {

    @Test
    public void testEquation1() {
        String equation = "2*x+5=17";
        assertTrue(EquationEvaluator.evaluate(equation, 6));
    }

    @Test
    public void testEquation2() {
        String equation = "-1.3*5/x=1.2";
        assertFalse(EquationEvaluator.evaluate(equation, 5));
    }

    @Test
    public void testEquation3() {
        String equation = "2*x*x=10";
        assertFalse(EquationEvaluator.evaluate(equation, 2));
    }

    @Test
    public void testEquation4() {
        String equation = "2*(x+6+x)+5=25";
        assertTrue(EquationEvaluator.evaluate(equation, 2));
    }

    @Test
    public void testEquation5() {
        String equation = "17=2*x+5";
        assertFalse(EquationEvaluator.evaluate(equation, 3));
    }

    @Test
    public void testEquation6() {
        String equation = "2*(x+5+-x)+5=10";
        assertFalse(EquationEvaluator.evaluate(equation, 5));
    }

    @Test
    public void testEquation7() {
        String equation = "2*((x+5+-x)+5)=10+(7+1)";
        assertFalse(EquationEvaluator.evaluate(equation, 5));
    }
}
