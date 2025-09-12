package com.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
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
    
    @Nested
    @DisplayName("Addition Operation Tests")
    class AdditionTests {
        
        @Test
        @DisplayName("Should add two positive numbers correctly")
        void shouldAddPositiveNumbers() throws CalculatorException {
            assertEquals(8.0, calculator.add(5.0, 3.0), 0.0001, 
                "Adding 5.0 + 3.0 should equal 8.0");
            assertEquals(13.0, calculator.add(10.5, 2.5), 0.0001,
                "Adding 10.5 + 2.5 should equal 13.0");
        }
        
        @Test
        @DisplayName("Should add negative numbers correctly")
        void shouldAddNegativeNumbers() throws CalculatorException {
            assertEquals(-8.0, calculator.add(-5.0, -3.0), 0.0001,
                "Adding -5.0 + (-3.0) should equal -8.0");
            assertEquals(-5.0, calculator.add(-10.0, 5.0), 0.0001,
                "Adding -10.0 + 5.0 should equal -5.0");
            assertEquals(7.0, calculator.add(10.0, -3.0), 0.0001,
                "Adding 10.0 + (-3.0) should equal 7.0");
        }
        
        @Test
        @DisplayName("Should handle zero addition correctly")
        void shouldHandleZeroAddition() throws CalculatorException {
            assertEquals(5.0, calculator.add(0.0, 5.0), 0.0001,
                "Adding 0.0 + 5.0 should equal 5.0");
            assertEquals(5.0, calculator.add(5.0, 0.0), 0.0001,
                "Adding 5.0 + 0.0 should equal 5.0");
            assertEquals(0.0, calculator.add(0.0, 0.0), 0.0001,
                "Adding 0.0 + 0.0 should equal 0.0");
        }
        
        @Test
        @DisplayName("Should handle edge cases with infinity and NaN")
        void shouldHandleEdgeCases() throws CalculatorException {
            assertEquals(Double.POSITIVE_INFINITY, 
                calculator.add(Double.MAX_VALUE, Double.MAX_VALUE),
                "Adding MAX_VALUE + MAX_VALUE should overflow to POSITIVE_INFINITY");
            
            assertEquals(Double.POSITIVE_INFINITY, 
                calculator.add(Double.POSITIVE_INFINITY, 5.0),
                "Adding POSITIVE_INFINITY + 5.0 should equal POSITIVE_INFINITY");
            
            assertEquals(Double.NEGATIVE_INFINITY, 
                calculator.add(Double.NEGATIVE_INFINITY, 5.0),
                "Adding NEGATIVE_INFINITY + 5.0 should equal NEGATIVE_INFINITY");
            
            assertTrue(Double.isNaN(calculator.add(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)),
                "Adding POSITIVE_INFINITY + NEGATIVE_INFINITY should equal NaN");
        }
        
        @Test
        @DisplayName("Should handle floating-point precision correctly")
        void shouldHandlePrecision() throws CalculatorException {
            double result = calculator.add(0.1, 0.2);
            assertEquals(0.3, result, 0.0001,
                "Adding 0.1 + 0.2 should approximately equal 0.3");
            
            assertEquals(2.0000000000003, 
                calculator.add(1.0000000000001, 1.0000000000002), 0.0000000000001,
                "Adding very precise numbers should maintain precision");
            
            assertEquals(3e-15, calculator.add(1e-15, 2e-15), 1e-16,
                "Adding very small numbers should work correctly");
        }
        
        @Test
        @DisplayName("Should handle large numbers correctly")
        void shouldHandleLargeNumbers() throws CalculatorException {
            assertEquals(3e100, calculator.add(1e100, 2e100), 1e99,
                "Adding large numbers should work correctly");
            
            assertEquals(0.0, calculator.add(-Double.MAX_VALUE, Double.MAX_VALUE), 0.0001,
                "Adding -MAX_VALUE + MAX_VALUE should equal 0.0");
        }
        
        @Test
        @DisplayName("Should handle boundary conditions")
        void shouldHandleBoundaryConditions() throws CalculatorException {
            assertEquals(Double.MIN_VALUE * 2, 
                calculator.add(Double.MIN_VALUE, Double.MIN_VALUE), Double.MIN_VALUE,
                "Adding MIN_VALUE + MIN_VALUE should work correctly");
            
            assertEquals(1.0, calculator.add(Double.MIN_VALUE, 1.0), 0.0001,
                "Adding MIN_VALUE + 1.0 should approximately equal 1.0");
        }
        
        @ParameterizedTest
        @CsvSource({
            "5.0, 3.0, 8.0",
            "10.5, 2.5, 13.0",
            "0.1, 0.2, 0.3",
            "-5.0, -3.0, -8.0",
            "-10.0, 5.0, -5.0",
            "10.0, -3.0, 7.0",
            "0.0, 5.0, 5.0",
            "5.0, 0.0, 5.0"
        })
        @DisplayName("Should add various number combinations correctly")
        void shouldAddNumberCombinations(double a, double b, double expected) throws CalculatorException {
            assertEquals(expected, calculator.add(a, b), 0.0001,
                String.format("Adding %.1f + %.1f should equal %.1f", a, b, expected));
        }
        
        @Test
        @DisplayName("Should throw InvalidInputException for NaN inputs")
        void shouldThrowExceptionForNaNInputs() {
            assertThrows(InvalidInputException.class, 
                () -> calculator.add(Double.NaN, 5.0),
                "Should throw InvalidInputException when first parameter is NaN");
            
            assertThrows(InvalidInputException.class, 
                () -> calculator.add(5.0, Double.NaN),
                "Should throw InvalidInputException when second parameter is NaN");
        }
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


