/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicexercises;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AL
 */
public class EuclideanGCDTest {
    
    public EuclideanGCDTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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

    /**
     * Test of euclideanGCD method, of class EuclideanGCD.
     */
    @Test
    public void testEuclideanGCD() {
        System.out.println("euclideanGCD");
        int a = 0;
        int b = 0;
        int expResult = 0;
        int result = EuclideanGCD.euclideanGCD(a, b);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testEuclideanGCDWithFibonacciNumbers() {
        System.out.println("euclideanGCD on Fibonacci numbers");
        int fibo1 = 0;
        int fibo2 = 1;
        int fiboSum;
        while (fibo2 < Integer.MAX_VALUE/8) {
            fiboSum = fibo1 + fibo2;
            fibo1 = fibo2;
            fibo2 = fiboSum;
            assertEquals(1, EuclideanGCD.euclideanGCD(fibo1, fibo2));
        }
    }

}
