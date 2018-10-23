/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicexercises;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

//  import static basicexercises.LegendreSymbol;

/**
 *
 * @author AL
 */
public class LegendreSymbolTest {
    
    /**
     * setUpClass() will generate a List of the first few consecutive primes. 
     * This constant determines how long that list will be. For example, if it's 
     * 1000, setUpClass() will generate a list of the primes between 1 and 1000.
     * It should not be greater than Integer.MAX_VALUE.
     */
    public static final int PRIME_LIST_THRESHOLD = 1000;
    
    /**
     * A List of the first few prime numbers, to be used in some of the tests.
     */
    private static List<Integer> primesList;
    
    /**
     * The size of primesList.
     */
    private static int primesListLength;
    
    /**
     * A List of composite numbers, which may or may not include 
     * PRIME_LIST_THRESHOLD.
     */
    private static List<Integer> compositesList;
    
    /**
     * A List of Fibonacci numbers.
     */
    private static List<Integer> fibonacciList;
    
    /**
     * There are the only nine negative numbers d such that the ring of 
     * algebraic integers of Q(sqrt(d)) is a unique factorization domain (though 
     * not necessarily Euclidean).
     */
    public static final int[] HEEGNER_NUMBERS = {-163, -67, -43, -19, -11, -7, -3, -2, -1};
    
    private static final int[] HEEGNER_COMPANION_PRIMES = new int[9];
    
    public LegendreSymbolTest() {
    }
    
    /**
     * Sets up a List of the first few consecutive primes, the first few 
     * composite numbers and the first few Fibonacci numbers. This provides most 
     * of what is needed for the tests.
     */
    @BeforeClass
    public static void setUpClass() {
        /* First, to generate a list of the first few consecutive primes, using 
        the sieve of Eratosthenes. */
        int threshold, halfThreshold;
        if (PRIME_LIST_THRESHOLD < 0) {
            threshold = (-1) * PRIME_LIST_THRESHOLD;
        } else {
            threshold = PRIME_LIST_THRESHOLD;
        }
        if (threshold % 2 == 1) {
            halfThreshold = (threshold + 1)/2;
        } else {
            halfThreshold = threshold/2;
        }
        primesList = new ArrayList<>();
        primesList.add(2); // Add 2 as a special case
        boolean[] primeFlags = new boolean[halfThreshold];
        for (int i = 0; i < halfThreshold; i++) {
            primeFlags[i] = true; // Presume all odd numbers prime for now
        }
        int currPrime = 3;
        int twiceCurrPrime, currIndex;
        while (currPrime < threshold) {
            primesList.add(currPrime);
            twiceCurrPrime = 2 * currPrime;
            for (int j = currPrime * currPrime; j < threshold; j += twiceCurrPrime) {
                currIndex = (j - 3)/2;
                primeFlags[currIndex] = false;
            }
            do {
                currPrime += 2;
                currIndex = (currPrime - 3)/2;
            } while (currIndex < (halfThreshold - 1) && primeFlags[currIndex] == false);
        }
        primesListLength = primesList.size();
        /* Now to make a list of composite numbers, from 4 up to and perhaps 
           including PRIME_LIST_THRESHOLD. */
        compositesList = new ArrayList<>();
        for (int c = 4; c < PRIME_LIST_THRESHOLD; c += 2) {
            compositesList.add(c);
            if (!primeFlags[c/2 - 1]) {
                compositesList.add(c + 1);
            }
        }
        System.out.println("setUpClass() has generated a list of the first " + primesListLength + " consecutive primes.");
        System.out.println("prime(" + primesListLength + ") = " + primesList.get(primesListLength - 1));
        System.out.println("There are " + (PRIME_LIST_THRESHOLD - (primesListLength + 1)) + " composite numbers up to " + PRIME_LIST_THRESHOLD + ".");
        // And now to make a list of Fibonacci numbers
        fibonacciList = new ArrayList<>();
        fibonacciList.add(0);
        fibonacciList.add(1);
        threshold = (Integer.MAX_VALUE - 3)/4; // Repurposing this variable
        currIndex = 2; // Also repurposing this one
        int currFibo = 1;
        while (currFibo < threshold) {
            currFibo = fibonacciList.get(currIndex - 2) + fibonacciList.get(currIndex - 1);
            fibonacciList.add(currFibo);
            currIndex++;
        }
        currIndex--; // Step one back to index last added Fibonacci number
        /* And last but not least, to make what I'm calling, for lack of a 
           better term, "the Heegner companion primes." */
        int absD, currDiff, currCompCand, currSqrIndex, currSqrDMult;
        boolean numNotFoundYet;
        for (int d = 0; d < HEEGNER_NUMBERS.length; d++) {
            absD = (-1) * HEEGNER_NUMBERS[d];
            currIndex = 0;
            do {
                currPrime = primesList.get(currIndex);
                currIndex++;
            } while (currPrime <= absD);
            numNotFoundYet = true;
            while (numNotFoundYet) {
                currCompCand = 4 * currPrime;
                currSqrIndex = 1;
                currDiff = absD;
                while (currDiff > 0) {
                    currSqrDMult = absD * currSqrIndex * currSqrIndex;
                    currDiff = currCompCand - currSqrDMult;
                    if (Math.sqrt(currDiff) == Math.floor(Math.sqrt(currDiff))) {
                        currDiff = 0;
                    }
                    currSqrIndex++;
                }
                if (currDiff < 0) {
                    numNotFoundYet = false;
                } else {
                    currIndex++;
                    currPrime = primesList.get(currIndex);
                }
            }
            HEEGNER_COMPANION_PRIMES[d] = currPrime;
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testLegendreSymbolWithQuadraticReciprocity() {
        System.out.println("Testing the Legendre symbol by way of quadratic reciprocity.");
        byte expResult, result;
        int p, q;
        for (int pindex = 1; pindex < primesList.size(); pindex++) {
            p = primesList.get(pindex);
            for (int qindex = pindex + 1; qindex < primesList.size(); qindex++) {
                q = primesList.get(qindex);
                expResult = LegendreSymbol.symbolLegendre(q, p);
                if (p % 4 == 3 && q % 4 == 3) { expResult *= -1; }
                result = LegendreSymbol.symbolLegendre(p, q);
                System.out.println("Legendre(" + p + ", " + q + ") = " + result);
                assertEquals(expResult, result);
            }
        }
    }
    
    @Test
    public void testLegendreSymbolWithMultiplicativity() {
        System.out.println("Testing the Legendre symbol by way of the multiplicative property.");
        byte expResult, result;
        int p, q, m;
        for (int pindex = 1; pindex < primesList.size(); pindex++) {
            p = primesList.get(pindex);
            for (int qindex = pindex + 1; qindex < primesList.size(); qindex++) {
                q = primesList.get(qindex);
                m = (p + 1) * q;
                expResult = LegendreSymbol.symbolLegendre(p + 1, p);
                expResult *= LegendreSymbol.symbolLegendre(q, p);
                result = LegendreSymbol.symbolLegendre(m, p);
                System.out.println("Legendre(" + m + ", " + p + ") = " + result);
                assertEquals(expResult, result);
            }
        }
    }
    
    @Test
    public void testLegendreSymbolOnGivenValues() {
        System.out.println("Testing the Legendre symbol on three specific pairs of numbers.");
        assertEquals(-1, LegendreSymbol.symbolLegendre(19, 7));
        assertEquals(0, LegendreSymbol.symbolLegendre(42, 7));
        assertEquals(1, LegendreSymbol.symbolLegendre(42, 19));
    }

    /*(ASTERISK)
     * Test of symbolLegendre method, of class LegendreSymbol.
     */
    // at Test
//    public void testSymbolLegendre() {
//        System.out.println("symbolLegendre");
//        int a = 0;
//        int p = 0;
//        byte expResult = 0;
//        byte result = LegendreSymbol.symbolLegendre(a, p);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
