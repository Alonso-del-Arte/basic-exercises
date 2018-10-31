/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicexercises;

import java.util.Stack;

/**
 * An implementation of a calculator that parses expressions in reverse Polish 
 * notation.
 * @author Alonso del Arte
 */
public class ReversePolishCalculator {
    
    private String calcHistory = "";
    
    /**
     * Gives the calculation history of this instance of the calculator.
     * @return A String alternating lines of reverse Polish notation expressions 
     * with lines starting with "= " and the numeric result of a calculation. If 
     * no calculations have been performed on the current instance, this will be 
     * an empty String.
     */
    public String history() {
        return calcHistory;
    }
    
    /**
     * Takes in an expression in reverse Polish notation and gives the numeric 
     * result of the calculation.
     * @param expression A String containing parseable numbers and arithmetic 
     * operator symbols. For example, "1 7 /", meaning "1 &divide; 7". The 
     * numbers and operators should be separated by spaces. The String should 
     * not contain parentheses.
     * @return The result, subject to the quandaries of machine precision. For 
     * example, given "1 7 /", this would be roughly 0.142857.
     * @throws IllegalArgumentException Thrown if the expression contains a 
     * symbol this function does not recognize as an operator symbol, e.g., "3 2 
     * ^", "5 4 + #".
     * @throws NumberFormatException Thrown if the expression contains a 
     * substring this function does not recognize as a parseable number, e.g., 
     * "one one +".
     */
    public double calculate(String expression) {
        calcHistory = calcHistory + expression + "\n";
        Stack<Double> stack = new Stack();
        String[] elems = expression.split(" ");
        double currVal = 0.0;
        double operand1, operand2;
        char currChar;
        for (String elem : elems) {
            try {
                currVal = Double.parseDouble(elem);
                stack.push(currVal);
            } catch (NumberFormatException nfe) {
                if (elem.length() == 1) {
                    operand2 = stack.pop();
                    operand1 = stack.pop();
                    currChar = elem.charAt(0);
                    switch (currChar) {
                        case '+':
                            currVal = operand1 + operand2;
                            break;
                        case '-':
                        case '\u2212':
                            currVal = operand1 - operand2;
                            break;
                        case '*':
                        case '\u00D7':
                            currVal = operand1 * operand2;
                            break;
                        case '/':
                        case '\u00F7':
                            currVal = operand1 / operand2;
                            break;
                        default:
                            String exceptionMessage = currChar + " is not a recognized operator symbol.";
                            throw new IllegalArgumentException(exceptionMessage);
                    }
                    stack.push(currVal);
                } else {
                    throw nfe; // Rethrow the exception
                }
            }
        }
        calcHistory = calcHistory + "= " + currVal;
        if (calcHistory.endsWith(".0")) {
            calcHistory = calcHistory.substring(0, calcHistory.length() - 2);
        }
        calcHistory = calcHistory + "\n";
        return currVal;
    }
    
}
