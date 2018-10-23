/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicexercises;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the class ReversePolishCalculator.
 * @author Alonso del Arte
 */
public class ReversePolishCalculatorTest {
    
    /**
     * Given the quandaries of machine precision, even when dealing with 
     * rational numbers only, it will occasionally happen that the result of a 
     * calculation is not quite what was expected. This small number is, in this 
     * case, an acceptable variance.
     */
    public static final double TEST_DELTA = 0.00001;
    
    private ReversePolishCalculator revPolCalc;
    
    @Before
    public void setUp() {
        revPolCalc = new ReversePolishCalculator();
    }
    
    /**
     * Test of history method, of class ReversePolishCalculator.
     */
    @Test
    public void testHistory() {
        System.out.println("history");
        String expResult = "";
        String result = revPolCalc.history();
        assertEquals(expResult, result);
        String expression = "1 1 +";
        revPolCalc.calculate(expression);
        expResult = expression + "\n= 2\n";
        result = revPolCalc.history();
        assertEquals(expResult, result);
        expression = "1 2 3 * - 4 /";
        revPolCalc.calculate(expression);
        expResult = expResult + expression + "\n= -1.25\n";
        result = revPolCalc.history();
        assertEquals(expResult, result);
    }

    /**
     * Test of calculate method, of class ReversePolishCalculator.
     */
    @Test
    public void testCalculate() {
        System.out.println("calculate");
        String expression = "1 1 +";
        double expResult = 2.0;
        double result = revPolCalc.calculate(expression);
        assertEquals(expResult, result, TEST_DELTA);
        expression = "1 3 /";
        expResult = 0.33333;
        result = revPolCalc.calculate(expression);
        assertEquals(expResult, result, TEST_DELTA);
    }

    /**
     * Test of calculate method, of class ReversePolishCalculator. This test 
     * gives the calculator the expression "3 2 + 8 4 * -", which comes from 
     * Samah Majadla's project prompt.
     */
    @Test
    public void testCalculateOnGivenExpression() {
        String expression = "3 2 + 8 4 * -";
        System.out.println("calculate \"" + expression + "\"");
        double expResult = -27.0;
        double result = revPolCalc.calculate(expression);
        assertEquals(expResult, result, TEST_DELTA);
    }
    
    /**
     * Test of calculate method, of class ReversePolishCalculator. This test 
     * gives the calculator the expression "1 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 
     * 2 1 2 / + / + / + / + / + / + / + / + / + / +", which is reverse Polish 
     * for "1 + 1/(2 + 1/(2 + 1/(2 + 1/(2 + 1/(2 + 1/(2 + 1/(2 + 1/(2 + 1/(2 + 
     * 1/2)))))))))". That should be a pretty decent approximation of &radic;2, 
     * roughly 1.414213562373.
     */
    @Test
    public void testCalculateOnContFracExpr() {
        String expression = "1 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 / + / + / + / + / + / + / + / + / + / +";
        System.out.println("calculate \"" + expression + "\"");
        double expResult = Math.sqrt(2);
        double result = revPolCalc.calculate(expression);
        assertEquals(expResult, result, TEST_DELTA);
    }
    
    /**
     * Test of calculate method, of class ReversePolishCalculator. Makes sure 
     * that division by zero causes an appropriate exception: either {@link 
     * IllegalArgumentException} or {@link ArithmeticException}.
     */
    @Test
    public void testDivisionByZeroCausesException() {
        String expression = "0 0 /";
        System.out.println("calculate \"" + expression + "\" should cause one of two specific exceptions");
        try {
            double result = revPolCalc.calculate(expression);
            String failMessage = "Calculating \"" + expression + "\" should have triggered an exception, not given result " + result + ".";
            fail(failMessage);
        } catch (IllegalArgumentException iae) {
            System.out.println("Calculating \"" + expression + "\" correctly triggered IllegalArgumentException: " + iae.getMessage());
        } catch (ArithmeticException ae) {
            System.out.println("Calculating \"" + expression + "\" correctly triggered ArithmeticException: " + ae.getMessage());
        } catch (Exception e) {
            String failMessage = "Calculating \"" + expression + "\" should have not have triggered " + e.getClass().getName();
            failMessage = failMessage + ": " + e.getMessage();
            fail(failMessage);
        }
    }
    
    /**
     * Test of calculate method, of class ReversePolishCalculator. An expression 
     * with alphabetic characters should cause an exception, like {@link 
     * NumberFormatException}.
     */
    @Test
    public void testCalculateAlphabeticExprCausesException() {
        String expression = "one one plus";
        System.out.println("calculate \"" + expression + "\" should cause one of two specific exceptions");
        try {
            double result = revPolCalc.calculate(expression);
            String failMessage = "Calculating \"" + expression + "\" should have triggered an exception, not given result " + result + ".";
            fail(failMessage);
        } catch (NumberFormatException nfe) {
            System.out.println("Calculating \"" + expression + "\" correctly triggered NumberFormatException: " + nfe.getMessage());
        } catch (IllegalArgumentException iae) {
            System.out.println("Calculating \"" + expression + "\" correctly triggered IllegalArgumentException: " + iae.getMessage());
        } catch (Exception e) {
            String failMessage = "Calculating \"" + expression + "\" should have not have triggered " + e.getClass().getName();
            failMessage = failMessage + ": " + e.getMessage();
            fail(failMessage);
        }
    }
    
    /**
     * Test of calculate method, of class ReversePolishCalculator. The minus 
     * sign ("&minus;", Unicode 0x2212) should work the same as the hyphen 
     * ("-", ASCII 0x2D).
     */
    @Test
    public void testCalculateWithMinusSign() {
        String expression = "0 1 \u2212";
        String exprWDash = expression.replace("\u2212", "-");
        System.out.println("calculate \"" + expression + "\" should work the same as " + exprWDash);
        double expResult = -1.0;
        double result = revPolCalc.calculate(expression);
        assertEquals(expResult, result, TEST_DELTA);
        double resultWDash = revPolCalc.calculate(exprWDash);
        assertEquals(result, resultWDash, TEST_DELTA);
    }

    /**
     * Test of calculate method, of class ReversePolishCalculator. The 
     * multiplication sign ("&times;", Unicode 0x00D7) should work the same as 
     * the asterisk ("*", ASCII 0x42).
     */
    @Test
    public void testCalculateWithMultCross() {
        String expression = "6 7 \u00D7";
        String exprWCross = expression.replace("\u00D7", "*");
        System.out.println("calculate \"" + expression + "\" should work the same as " + exprWCross);
        double expResult = 42.0;
        double result = revPolCalc.calculate(expression);
        assertEquals(expResult, result, TEST_DELTA);
        double resultWCross = revPolCalc.calculate(exprWCross);
        assertEquals(result, resultWCross, TEST_DELTA);
    }

    /**
     * Test of calculate method, of class ReversePolishCalculator. The obelus
     * ("&divide;", Unicode 0x00F7) should work the same as 
     * the forward slash ("/", ASCII 0x2F).
     */
    @Test
    public void testCalculateWithObelus() {
        String expression = "1 7 \u00F7";
        String exprWObelus = expression.replace("\u00F7", "/");
        System.out.println("calculate \"" + expression + "\" should work the same as " + exprWObelus);
        double expResult = 0.142857;
        double result = revPolCalc.calculate(expression);
        assertEquals(expResult, result, TEST_DELTA);
        double resultWSlash = revPolCalc.calculate(exprWObelus);
        assertEquals(result, resultWSlash, TEST_DELTA);
    }
    
}
