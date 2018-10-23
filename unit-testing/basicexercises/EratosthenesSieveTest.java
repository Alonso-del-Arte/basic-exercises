/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicexercises;

import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Collections;
import java.util.List;
// import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the class EratosthenesSieve.
 * @author Alonso del Arte
 */
public class EratosthenesSieveTest {
    
    /**
     * List of positive prime numbers less than 1000 obtained from Wolfram 
     * Mathematica with the command <code>Prime[Range[168]]</code>.
     */
    private final List<Integer> PRIMES_TO_1000 = new ArrayList<>();
    
    /**
     * There are 25 primes between 1 and 100: 2, 3, 5, 7, ..., 97.
     */
    private final int PRIME_PI_100 = 25;
    
    /**
     * There are 168 primes between 1 and 1000: 2, 3, 5, 7, ..., 997.
     */
    private final int PRIME_PI_1000 = 168;

    /**
     * There are 1229 primes between 1 and 10000: 2, 3, 5, 7, ..., 9973.
     */
    private final int PRIME_PI_10000 = 1229;
    
    private final EratosthenesSieve eratSieve = new EratosthenesSieve();
    
    public EratosthenesSieveTest() {
        int[] somePrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 
            47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 
            127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 
            193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 
            269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 
            349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 
            431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 
            503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 
            599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 
            673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 
            761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 
            857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 
            947, 953, 967, 971, 977, 983, 991, 997};
        for (Integer p : somePrimes) {
            PRIMES_TO_1000.add(p);
        }
    }
    
    /**
     * Test of getCount method, of class EratosthenesSieve. It should return -1 
     * on a fresh instance, or maybe that should cause an {@link 
     * IllegalStateException}. After being asked to compute primes up to 100, 
     * 1000 and 10000, it should return 25, 168, 1229, respectively (see 
     * <a href="https://oeis.org/A006880">Sloane's A006880</a>).
     */
    @Test
    public void testGetCount() {
        System.out.println("getCount");
        int expResult = -1;
        int result = eratSieve.getCount();
        assertEquals(expResult, result);
        int limit = 100;
        eratSieve.sieve(limit);
        expResult = PRIME_PI_100;
        result = eratSieve.getCount();
        System.out.println("Sieve implementation reports " + result + " primes less than " + limit + ".");
        assertEquals(expResult, result);
        limit *= 10;
        eratSieve.sieve(limit);
        expResult = PRIME_PI_1000;
        result = eratSieve.getCount();
        System.out.println("Sieve implementation reports " + result + " primes less than " + limit + ".");
        assertEquals(expResult, result);
        limit *= 10;
        eratSieve.sieve(limit);
        expResult = PRIME_PI_10000;
        result = eratSieve.getCount();
        System.out.println("Sieve implementation reports " + result + " primes less than " + limit + ".");
        assertEquals(expResult, result);
    }

    /**
     * Test of getLimit method, of class EratosthenesSieve. It should return -1 
     * on a fresh instance, or maybe that should cause an {@link 
     * IllegalStateException}?
     */
    @Test
    public void testGetLimit() {
        System.out.println("getLimit");
        int expResult = -1;
        int result = eratSieve.getLimit();
        assertEquals(expResult, result);
        expResult = 50;
        eratSieve.sieve(expResult);
        result = eratSieve.getLimit();
        assertEquals(expResult, result);
    }
    
    private void checkListOf1KPrimesOrMore(List<Integer> expPrimesList) {
        // First 168 numbers should match PRIMES_TO_1000
        for (int i = 0; i < PRIME_PI_1000; i++) {
            try {
                assertEquals(PRIMES_TO_1000.get(i), expPrimesList.get(i));
            } catch (IndexOutOfBoundsException ioobe) {
                String failMessage = "Trying to access prime(" + i + ") caused IndexOutOfBoundsException exception.";
                failMessage = failMessage + "\"" + ioobe.getMessage() + "\"";
                fail(failMessage);
            }
        }
        /* Remaining numbers should be in strictly ascending order, but more 
           importantly, none of them should be divisible by any of the first 168
           primes. */
        int prevPrime = expPrimesList.get(PRIME_PI_1000 - 1);
        int currPrime, currMod, primeModulus;
        String assertionMessage;
        for (int j = PRIME_PI_1000; j < expPrimesList.size(); j++) {
            currPrime = expPrimesList.get(j);
            assertTrue(prevPrime < currPrime);
            for (int k = 0; k < PRIME_PI_1000; k++) {
                primeModulus = PRIMES_TO_1000.get(k);
                currMod = currPrime % primeModulus;
                assertionMessage = currPrime + ", which is supposedly prime, should not be divisible by " + primeModulus;
                assertNotEquals(assertionMessage, 0, currMod);
            }
        }
    }

    /**
     * Test of sieve method, of class EratosthenesSieve.
     */
    @Test
    public void testSieve() {
        System.out.println("sieve");
        int limit = 100;
        List<Integer> expResult = new ArrayList<>();
        for (int i = 0; i < PRIME_PI_100; i++) {
            expResult.add(PRIMES_TO_1000.get(i));
        }
        List<Integer> result = eratSieve.sieve(limit);
        assertEquals(expResult, result);
        limit *= 10;
        result = eratSieve.sieve(limit);
        assertEquals(PRIMES_TO_1000, result);
        limit *= 10;
        result = eratSieve.sieve(limit);
        String assertionMessage = "Last number in list of primes to " + limit + " should not be greater than " + limit;
        assertFalse(assertionMessage, result.get(result.size() - 1) > limit);
        checkListOf1KPrimesOrMore(result);
    }
    
    /**
     * Test of sieve method, of class EratosthenesSieve. This test comes up with 
     * a pseudorandom number between 10010 and 11026 to use as a limit.
     */
    @Test
    public void testSieveOnRandomLimit() {
        System.out.println("sieve on pseudorandomly chosen limit");
        int limit = 10010 + (int) Math.floor(Math.random() * 1016);
        List<Integer> result = eratSieve.sieve(limit);
        System.out.println("Given a limit of " + limit + ", sieve implementation reports greatest prime to be " + result.get(result.size() - 1) + ".");
        int len = result.size();
        System.out.println("Sieve implementation reports " + len + " primes less than or equal to " + limit + ".");
        String assertionMessage = "Last number in list of primes to " + limit + " should not be greater than " + limit;
        assertFalse(assertionMessage, result.get(len - 1) > limit);
        assertionMessage = "List of primes with limit " + limit + " should have at least " + (PRIME_PI_10000 + 2) + " numbers.";
        assertTrue(assertionMessage, len > (PRIME_PI_10000 + 1));
        assertionMessage = "List of primes with limit " + limit + " should have no more than 1337 numbers.";
        assertTrue(assertionMessage, len < 1337);
        checkListOf1KPrimesOrMore(result);
    }
    
    /**
     * Test of sieve method, of class EratosthenesSieve. This one checks that a 
     * negative limit causes sieve() to return a list of negative primes. Or 
     * should it cause an {@link IllegalArgumentException}?
     */
    @Test
    public void testSieveOnNegLimitGivesNegPrimes() {
        System.out.println("sieve on a negative limit should give negative primes");
        int limit = -100;
        List<Integer> expResult = new ArrayList<>();
        for (int i = 0; i < PRIME_PI_100; i++) {
            expResult.add(0, (-1) * PRIMES_TO_1000.get(i));
        }
        List<Integer> result = eratSieve.sieve(limit);
        String assertionMessage = "Limit of " + limit + " should give list with " + PRIME_PI_100 + " numbers.";
        assertEquals(assertionMessage, PRIME_PI_100, result.size());
        Collections.sort(result); // Just in case the list is in descending order
        System.out.println("Given a limit of " + limit + ", sieve implementation reports " + result.get(0) + " as prime.");
        assertEquals(expResult, result);
    }

    /**
     * Test of sieve method, of class EratosthenesSieve. This one checks that a 
     * negative limit causes sieve() to return an empty list. If this behavior 
     * is preferred, move the Ignore annotation to 
     * testSieveOnNegLimitGivesNegPrimes().
     */
    @Test
    public void testSieveOnNegLimitGivesEmptyList() {
        System.out.println("sieve on a negative limit should give an empty list");
        int limit = -100;
        List<Integer> expResult = new ArrayList<>(); // Not actually gonna put anything in it
        List<Integer> result = eratSieve.sieve(limit);
        assertEquals(expResult, result);
    }
    
}
