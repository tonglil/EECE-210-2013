package com.series;

import static org.junit.Assert.*;

import org.junit.Test;

public class GeometricSeriesTest {
    @Test
    public void testMainNormal() {
        // Tests if a randomly selected input evaluates to the correct value in main
        // Input: a = 5, n = 3, r =2
        // Expected output: hasResult == true, result == 35

        try {
            GeometricSeries gs = new GeometricSeries();
            String[] arr = {"GeometricSeries", "5", "3", "2"};
            gs.main(arr);
            assertTrue(gs.hasResult);
            assertTrue(gs.result == 35);
        } catch (NumberFormatException nfe) {
            fail("main throws unexpected exception");
        }
    }

    @Test
    public void testPowPositiveNormal() {
        // Tests if a randomly selected input evaluates to the correct value in powPositive
        // Input: base = 7, exp = 3
        // Expected Output: 343

        GeometricSeries gs = new GeometricSeries();
        try {
            int result = gs.powPositive(7, 3);
            assertTrue(result == 343);
        } catch (Exception e) {
            fail("powPositive throws unexpected exception");
        }
    }

    @Test
    public void testPowPositiveNegative() {
        // Tests if passing a negative exponent throws an exception
        // Input: base = 5, exp = -1
        // Expected Output: Exception thrown

        GeometricSeries gs = new GeometricSeries();
        try {
            int result = gs.powPositive(5, -1);
            fail("powPositive does not throw exception on negative exponent");
        } catch (Exception e) {
            // Do nothing, test succeeded if this point reached
        }
    }
}
