/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicexercises;

/**
 *
 * @author Alonso del Arte
 */
public class LegendreSymbol {
    
//    public static byte symbolLegendre(int a, int p) {
//        int expo = (p - 1)/2;
//        int symbol = ((int) Math.pow(a, expo)) % p;
//        if (symbol == p - 1) { symbol = -1; }
//        return (byte) symbol;
//    }
    
    public static byte symbolLegendre(int a, int p) {
                if (euclideanGCD(a, p) > 1) {
            return 0;
        }
        int oddPrime = Math.abs(p); // Making sure p is positive
        int exponent = (oddPrime - 1)/2;
        int modStop = oddPrime - 2;
        int adjA = a;
        if (adjA > (oddPrime - 1)) {
            adjA %= oddPrime;
        }
        if (adjA == (oddPrime - 1)) {
            adjA = -1;
        }
        while (adjA < -1) {
            adjA += oddPrime;
        }
        int power = adjA;
        for (int i = 1; i < exponent; i++) {
            power *= adjA;
            while (power > modStop) {
                power -= oddPrime;
            }
            while (power < -1) {
                power += oddPrime;
            }
        }
return (byte) power;
    }
    
    
        /**
     * Computes the greatest common divisor (GCD) of two purely real integers by 
     * using the Euclidean algorithm.
     * @param a One of the two integers. May be negative, need not be greater 
     * than the other.
     * @param b One of the two integers. May be negative, need not be smaller 
     * than the other.
     * @return The GCD as an integer.
     * If one of a or b is 0 and the other is nonzero, the result will be the 
     * nonzero number.
     * If both a and b are 0, then the result will be 0, which is perhaps 
     * technically wrong, but I think it's good enough for the purpose here.
     */
    public static int euclideanGCD(int a, int b) {
        int currA, currB, currRemainder;
        if (a < b) {
            currA = b;
            currB = a;
        } else {
            currA = a;
            currB = b;
        }
        while (currB != 0) {
            currRemainder = currA % currB;
            currA = currB;
            currB = currRemainder;
        }
        if (currA < 0) {
            currA *= -1;
        }
        return currA;
}
    
    
}
