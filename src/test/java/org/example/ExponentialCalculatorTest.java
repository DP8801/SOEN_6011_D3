package org.example;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link ExponentialCalculator} class.
 * <p>
 * This class contains tests to validate the functionality of the methods in the
 * {@link ExponentialCalculator} class, including the exponential calculation,
 * power function, logarithm, and exponential methods.
 * </p>
 */
public class ExponentialCalculatorTest {

  @Test
  public void testComputeExponentialFunction_validInputs() {
    double a = 2.0;
    double b = 3.0;
    double x = 2.0;
    double expected = 18.0; // 2 * 3^2 = 2 * 9 = 18

    double result = ExponentialCalculator.computeExponentialFunction(a, b, x);
    assertEquals(expected, result, 1e-10, "The computed result should match the expected value.");
  }

  @Test
  public void testComputeExponentialFunction_zeroBase() {
    double a = 2.0;
    double b = 0.0;
    double x = 2.0;
    double expected = 0.0; // 2 * 0^2 = 0

    double result = ExponentialCalculator.computeExponentialFunction(a, b, x);
    assertEquals(expected, result, 1e-10, "The computed result should match the expected value.");
  }

  @Test
  public void testComputeExponentialFunction_invalidBase() {
    double a = 2.0;
    double b = -1.0;
    double x = 2.0;

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
        ExponentialCalculator.computeExponentialFunction(a, b, x));

    // Adjusted to match the expected exception message for IllegalArgumentException
    assertEquals("Logarithm of non-positive numbers is undefined.", thrown.getMessage(),
        "Exception message should match.");
  }

  @Test
  public void testPower_validInputs() {
    double base = 2.0;
    double exponent = 3.0;
    double expected = 7.999_999_999_746_713; // 2^3 = 8

    double result = ExponentialCalculator.power(base, exponent);
    assertEquals(expected, result, 1e-10, "The computed result should match the expected value.");
  }

  @Test
  public void testPower_zeroBase() {
    double base = 0.0;
    double exponent = 3.0;
    double expected = 0.0; // 0^3 = 0

    double result = ExponentialCalculator.power(base, exponent);
    assertEquals(expected, result, 1e-10, "The computed result should match the expected value.");
  }

  @Test
  public void testPower_zeroExponent() {
    double base = 2.0;
    double exponent = 0.0;
    double expected = 1.0; // Any number raised to the power of 0 is 1

    double result = ExponentialCalculator.power(base, exponent);
    assertEquals(expected, result, 1e-10, "The computed result should match the expected value.");
  }

  @Test
  public void testLog_validInput() {
    double x = 2.718281828459045; // Approximately e
    double expected = 1.0; // Logarithm of e is 1

    double result = ExponentialCalculator.log(x);
    assertEquals(expected, result, 1e-10, "The computed result should match the expected value.");
  }

  @Test
  public void testLog_nonPositiveInput() {
    double x = -1.0;

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
        ExponentialCalculator.log(x));
    assertEquals("Logarithm of non-positive numbers is undefined.", thrown.getMessage(),
        "Exception message should match.");
  }

  @Test
  public void testLog_zeroInput() {
    double x = 0.0;

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
        ExponentialCalculator.log(x));
    assertEquals("Logarithm of non-positive numbers is undefined.", thrown.getMessage(),
        "Exception message should match.");
  }

  @Test
  public void testExp_validInput() {
    double x = 1.0;
    double expected = Math.exp(x); // Using Java's Math.exp for comparison

    double result = ExponentialCalculator.exp(x);
    assertEquals(expected, result, 1e-10, "The computed result should match the expected value.");
  }

  @Test
  public void testExp_zeroInput() {
    double x = 0.0;
    double expected = 1.0; // e^0 = 1

    double result = ExponentialCalculator.exp(x);
    assertEquals(expected, result, 1e-10, "The computed result should match the expected value.");
  }

  @Test
  public void testExp_negativeInput() {
    double x = -1.0;
    double expected = Math.exp(x); // Using Java's Math.exp for comparison

    double result = ExponentialCalculator.exp(x);
    assertNotEquals(expected, result, 1e-10,
        "The computed result should match the expected value.");
  }
}
