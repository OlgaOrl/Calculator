package com.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for the Calculator class.
 * Tests all mathematical operations, memory functionality, history tracking,
 * and exception handling scenarios.
 */
class CalculatorTest {
    
    private Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    
    // Basic Arithmetic Tests
    
    @ParameterizedTest
    @CsvSource({
        "5.0, 3.0, 8.0",
        "0.0, 0.0, 0.0",
        "-5.0, 3.0, -2.0",
        "1.5, 2.5, 4.0"
    })
    void testAdd(double a, double b, double expected) throws CalculatorException {
        assertEquals(expected, calculator.add(a, b), 0.0001);
    }
    
    @Test
    void testAddWithNaN() {
        assertThrows(InvalidInputException.class, () -> calculator.add(Double.NaN, 5.0));
        assertThrows(InvalidInputException.class, () -> calculator.add(5.0, Double.NaN));
    }
    
    @ParameterizedTest
    @CsvSource({
        "10.0, 3.0, 7.0",
        "0.0, 5.0, -5.0",
        "-3.0, -2.0, -1.0"
    })
    void testSubtract(double a, double b, double expected) throws CalculatorException {
        assertEquals(expected, calculator.subtract(a, b), 0.0001);
    }
    
    @ParameterizedTest
    @CsvSource({
        "4.0, 3.0, 12.0",
        "0.0, 5.0, 0.0",
        "-2.0, 3.0, -6.0"
    })
    void testMultiply(double a, double b, double expected) throws CalculatorException {
        assertEquals(expected, calculator.multiply(a, b), 0.0001);
    }
    
    @ParameterizedTest
    @CsvSource({
        "10.0, 2.0, 5.0",
        "15.0, 3.0, 5.0",
        "-8.0, 2.0, -4.0"
    })
    void testDivide(double a, double b, double expected) throws CalculatorException {
        assertEquals(expected, calculator.divide(a, b), 0.0001);
    }
    
    @Test
    void testDivideByZero() {
        assertThrows(DivisionByZeroException.class, () -> calculator.divide(10.0, 0.0));
    }
    
    // Advanced Mathematical Operations Tests
    
    @ParameterizedTest
    @CsvSource({
        "2.0, 3.0, 8.0",
        "5.0, 2.0, 25.0",
        "3.0, 0.0, 1.0"
    })
    void testPower(double base, double exponent, double expected) throws CalculatorException {
        assertEquals(expected, calculator.power(base, exponent), 0.0001);
    }
    
    @ParameterizedTest
    @CsvSource({
        "16.0, 4.0",
        "25.0, 5.0",
        "0.0, 0.0",
        "1.0, 1.0"
    })
    void testSquareRoot(double number, double expected) throws CalculatorException {
        assertEquals(expected, calculator.squareRoot(number), 0.0001);
    }
    
    @Test
    void testSquareRootNegative() {
        assertThrows(InvalidInputException.class, () -> calculator.squareRoot(-4.0));
    }
    
    @ParameterizedTest
    @CsvSource({
        "100.0, 10.0, 10.0",
        "200.0, 50.0, 100.0",
        "80.0, 25.0, 20.0"
    })
    void testPercentage(double number, double percent, double expected) throws CalculatorException {
        assertEquals(expected, calculator.percentage(number, percent), 0.0001);
    }
    
    @ParameterizedTest
    @CsvSource({
        "-5.0, 5.0",
        "3.0, 3.0",
        "0.0, 0.0"
    })
    void testAbsolute(double number, double expected) throws CalculatorException {
        assertEquals(expected, calculator.absolute(number), 0.0001);
    }
    
    @ParameterizedTest
    @CsvSource({
        "0, 1",
        "1, 1", 
        "5, 120",
        "10, 3628800"
    })
    void testFactorial(int number, long expected) throws CalculatorException {
        assertEquals(expected, calculator.factorial(number));
    }
    
    @Test
    void testFactorialNegative() {
        assertThrows(InvalidInputException.class, () -> calculator.factorial(-1));
    }
    
    @Test
    void testFactorialOverflow() {
        assertThrows(InvalidInputException.class, () -> calculator.factorial(25));
    }
    
    // Memory Operations Tests
    
    @Test
    void testMemoryOperations() throws CalculatorException {
        // Test store and recall
        calculator.memoryStore(42.5);
        assertEquals(42.5, calculator.memoryRecall(), 0.0001);
        
        // Test memory add
        calculator.memoryAdd(7.5);
        assertEquals(50.0, calculator.memoryRecall(), 0.0001);
        
        // Test memory clear
        calculator.memoryClear();
        assertEquals(0.0, calculator.memoryRecall(), 0.0001);
    }
    
    @Test
    void testMemoryStoreNaN() {
        assertThrows(InvalidInputException.class, () -> calculator.memoryStore(Double.NaN));
    }
    
    // History Tests
    
    @Test
    void testCalculationHistory() throws CalculatorException {
        calculator.add(5.0, 3.0);
        calculator.multiply(2.0, 4.0);
        
        var history = calculator.getHistory();
        assertEquals(2, history.size());
        assertTrue(history.get(0).contains("5.0 + 3.0 = 8"));
        assertTrue(history.get(1).contains("2.0 * 4.0 = 8"));
        
        assertEquals(history.get(1), calculator.getLastCalculation());
    }
    
    @Test
    void testClearHistory() throws CalculatorException {
        calculator.add(1.0, 2.0);
        calculator.clearHistory();
        assertTrue(calculator.getHistory().isEmpty());
        assertNull(calculator.getLastCalculation());
    }
    
    // Utility Methods Tests
    
    @ParameterizedTest
    @CsvSource({
        "3.14159, 2, 3.14",
        "2.678, 1, 2.7",
        "5.0, 0, 5.0"
    })
    void testRound(double value, int places, double expected) throws CalculatorException {
        assertEquals(expected, calculator.round(value, places), 0.0001);
    }
    
    @Test
    void testRoundNegativePlaces() {
        assertThrows(InvalidInputException.class, () -> calculator.round(3.14, -1));
    }
    
    @ParameterizedTest
    @ValueSource(doubles = {1.0, 5.0, -3.0, 0.0})
    void testIsIntegerTrue(double value) {
        assertTrue(calculator.isInteger(value));
    }
    
    @ParameterizedTest
    @ValueSource(doubles = {1.5, 3.14, -2.7})
    void testIsIntegerFalse(double value) {
        assertFalse(calculator.isInteger(value));
    }
    
    @Test
    void testFormatResult() {
        assertEquals("5", calculator.formatResult(5.0));
        assertEquals("3.14", calculator.formatResult(3.14));
        assertEquals("NaN", calculator.formatResult(Double.NaN));
        assertEquals("∞", calculator.formatResult(Double.POSITIVE_INFINITY));
        assertEquals("-∞", calculator.formatResult(Double.NEGATIVE_INFINITY));
    }
    
    @Test
    void testToString() {
        String result = calculator.toString();
        assertNotNull(result);
        assertTrue(result.contains("Calculator"));
        assertTrue(result.contains("memory=0"));
        assertTrue(result.contains("history entries=0"));
    }
    
    @Test
    void testConstants() {
        assertEquals(Math.PI, Calculator.PI, 0.0001);
        assertEquals(Math.E, Calculator.E, 0.0001);
    }
}
