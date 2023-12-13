package org.vitaliistf.mathhelper.validation;

import org.junit.jupiter.api.Test;
import org.vitaliistf.mathhelper.validation.EquationValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EquationValidatorTest {

    @Test
    public void testValidEquation1() {
        String equation = "2*x+5=17";
        assertTrue(EquationValidator.isValid(equation));
    }

    @Test
    public void testValidEquation2() {
        String equation = "-1.3*5/x=1.2";
        assertTrue(EquationValidator.isValid(equation));
    }

    @Test
    public void testValidEquation3() {
        String equation = "2*x*x=10";
        assertTrue(EquationValidator.isValid(equation));
    }

    @Test
    public void testValidEquation4() {
        String equation = "2*(x+5+x)+5=10";
        assertTrue(EquationValidator.isValid(equation));
    }

    @Test
    public void testValidEquation5() {
        String equation = "17=2*x+5";
        assertTrue(EquationValidator.isValid(equation));
    }

    @Test
    public void testValidEquation6() {
        String equation = "2*(x+5+-x)+5=10";
        assertTrue(EquationValidator.isValid(equation));
    }

    @Test
    public void testValidEquation7() {
        String equation = "2*((x+5+-x)+5)=10+(7+1)";
        assertTrue(EquationValidator.isValid(equation));
    }

    @Test
    public void testInvalidEquation1() {
        String equation = "2*x+5=abc"; // Некоректний символ "abc"
        assertFalse(EquationValidator.isValid(equation));
    }

    @Test
    public void testInvalidEquation2() {
        String equation = "2x+5=17"; // Відсутній оператор між 2 і x
        assertFalse(EquationValidator.isValid(equation));
    }

    @Test
    public void testInvalidEquation3() {
        String equation = "2*(x+5=10"; // Неправильно розташована дужка
        assertFalse(EquationValidator.isValid(equation));
    }

    @Test
    public void testInvalidEquation4() {
        String equation = "17=2*x++5"; // Послідовність операторів (++)
        assertFalse(EquationValidator.isValid(equation));
    }

    @Test
    public void testInvalidEquation5() {
        String equation = "17=2*x1+5"; // Відсутній оператор між x і 1
        assertFalse(EquationValidator.isValid(equation));
    }

    @Test
    public void testInvalidEquation6() {
        String equation = "2*(x+5+x))+5=10"; // Неправильно розташована дужка
        assertFalse(EquationValidator.isValid(equation));
    }

    @Test
    public void testInvalidEquation7() {
        String equation = "2*(x+5+x))+5="; // Після знаку рівності нічого немає
        assertFalse(EquationValidator.isValid(equation));
    }
}
