/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicexercises;

import java.util.List;
import java.util.ArrayList;

/**
 * Defines an object that computes lists of consecutive primes using the sieve 
 * of Eratosthenes algorithm. An instance can recall the most recent prior limit 
 * argument and the most recent count of prime numbers sieved.
 * @author Alonso del Arte
 */
public class EratosthenesSieve {
    
    private int lastSieveCount = -1;
    private int lastSieveLimit = -1;
    
    /**
     * Gives total number of primes from the most recent sieve operation.
     * @return A positive integer equal to the length of the most recent list of 
     * primes given, or -1 if {@link #sieve(int) sieve} has not been called on 
     * the current instance.
     */
    public int getCount() {
        return lastSieveCount;
    }
    
    /**
     * Gives the previous limit on the most recent sieve operation.
     * @return The integer that was most recently passed to {@link #sieve(int) 
     * sieve} as a limit argument, or -1 if that has not been called on the 
     * current instance.
     */
    public int getLimit() {
        return lastSieveLimit;
    }
    
    /**
     * Using the sieve of Eratosthenes algorithm, gives a list of prime numbers 
     * between 0 and a specified limit.
     * @param limit The integer at which to stop the sieve process. May be 0 or 
     * negative.
     * @return A List of Integer objects with the primes. May be an empty list 
     * if the limit parameter was -1, 0 or 1. If the limit parameter is greater 
     * than 2, the primes will be in ascending order. If the limit parameter is 
     * less than -2, the primes will be in descending order. If the limit 
     * parameter is itself prime, it should appear last in the List.
     */
    public List<Integer> sieve(int limit) {
        lastSieveLimit = limit;
        int absLimit = limit;
        boolean negativeFlag = false;
        if (limit < 0) {
            absLimit *= -1;
            negativeFlag = true;
        }
        List<Integer> primesList = new ArrayList<>();
        primesList.add(2); // Add 2 as a special case
        primesList.add(3); // Also add 3 as a special case
        // All subsequent primes are either of the form 6k - 1 or 6k + 1
        for (int i = 6; i <= absLimit; i += 6) {
            primesList.add(i - 1);
            primesList.add(i + 1);
        }
        int currIndexA = 2; // This should correspond to 5
        int currMultA, currMultB, currIndexB, currMod;
        while (currIndexA < primesList.size() - 1) {
            currMultA = primesList.get(currIndexA);
            currIndexB = currIndexA + 1;
            while (currIndexB < primesList.size()) {
                currMultB = primesList.get(currIndexB);
                currMod = currMultB % currMultA;
                if (currMod == 0) {
                    primesList.remove(currIndexB);
                } else {
                    currIndexB++;
                }
            }
            currIndexA++;
        }
        while (primesList.get(primesList.size() - 1) > absLimit) {
            primesList.remove(primesList.size() - 1);
        }
        if (negativeFlag) {
            for (int i = 0; i < primesList.size(); i++) {
                primesList.set(i, (-1) * primesList.get(i));
            }
        }
        lastSieveCount = primesList.size();
        return primesList;
    }
    
}
