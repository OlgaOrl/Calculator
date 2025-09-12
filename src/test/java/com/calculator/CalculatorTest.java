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
@DisplayName("Calculator Tests")
class CalculatorTest {
    
    private Calculator calculator;
    private static final double DELTA = 0.0001;
    
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
            assertEquals(8.0, calculator.add(5.0, 3.0), DELTA, 
                "Adding 5.0 + 3.0 should equal 8.0");
            assertEquals(13.0, calculator.add(10.5, 2.5), DELTA,
                "Adding 10.5 + 2.5 should equal 13.0");
        }
        
        @Test
        @DisplayName("Should throw InvalidInputException for NaN inputs")
        void shouldThrowExceptionForNaNInputs() {
            assertThrows(InvalidInputException.class, 
                () -> calculator.add(Double.NaN, 5.0),
                "Should throw InvalidInputException when first operand is NaN");
            
            assertThrows(InvalidInputException.class, 
                () -> calculator.add(5.0, Double.NaN),
                "Should throw InvalidInputException when second operand is NaN");
        }
    }
    
    @Nested
    @DisplayName("Subtraction Operation Tests")
    class SubtractionTests {
        
        @Test
        @DisplayName("Should subtract two positive numbers correctly")
        void shouldSubtractPositiveNumbers() throws CalculatorException {
            assertEquals(7.0, calculator.subtract(10.0, 3.0), DELTA,
                "Subtracting 10.0 - 3.0 should equal 7.0");
        }
    }
    
    // Simple parameterized tests for basic operations
    @ParameterizedTest
    @CsvSource({
        "10.0, 3.0, 7.0",
        "0.0, 5.0, -5.0",
        "-3.0, -2.0, -1.0"
    })
    void testSubtract(double a, double b, double expected) throws CalculatorException {
        assertEquals(expected, calculator.subtract(a, b), DELTA);
    }
    
    @ParameterizedTest
    @CsvSource({
        "4.0, 3.0, 12.0",
        "0.0, 5.0, 0.0",
        "-2.0, 3.0, -6.0"
    })
    void testMultiply(double a, double b, double expected) throws CalculatorException {
        assertEquals(expected, calculator.multiply(a, b), DELTA);
    }
    
    @ParameterizedTest
    @CsvSource({
        "10.0, 2.0, 5.0",
        "15.0, 3.0, 5.0",
        "-8.0, 2.0, -4.0"
    })
    void testDivide(double a, double b, double expected) throws CalculatorException {
        assertEquals(expected, calculator.divide(a, b), DELTA);
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
        assertEquals(expected, calculator.power(base, exponent), DELTA);
    }
    
    @ParameterizedTest
    @CsvSource({
        "16.0, 4.0",
        "25.0, 5.0",
        "0.0, 0.0",
        "1.0, 1.0"
    })
    void testSquareRoot(double number, double expected) throws CalculatorException {
        assertEquals(expected, calculator.squareRoot(number), DELTA);
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
        assertEquals(expected, calculator.percentage(number, percent), DELTA);
    }
    
    @ParameterizedTest
    @CsvSource({
        "-5.0, 5.0",
        "3.0, 3.0",
        "0.0, 0.0"
    })
    void testAbsolute(double number, double expected) throws CalculatorException {
        assertEquals(expected, calculator.absolute(number), DELTA);
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
        assertEquals(42.5, calculator.memoryRecall(), DELTA);
        
        // Test memory add
        calculator.memoryAdd(7.5);
        assertEquals(50.0, calculator.memoryRecall(), DELTA);
        
        // Test memory clear
        calculator.memoryClear();
        assertEquals(0.0, calculator.memoryRecall(), DELTA);
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
        assertEquals(expected, calculator.round(value, places), DELTA);
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
        assertEquals(Math.PI, Calculator.PI, DELTA);
        assertEquals(Math.E, Calculator.E, DELTA);
    }
    
    @Nested
    @DisplayName("User-Focused Multiplication Tests")
    class UserMultiplicationTests {
        
        @Test
        @DisplayName("User can multiply two positive numbers correctly")
        void userCanMultiplyPositiveNumbers() throws CalculatorException {
            assertEquals(15.0, calculator.multiply(5.0, 3.0), DELTA);
            assertEquals(24.0, calculator.multiply(6.0, 4.0), DELTA);
            assertEquals(42.0, calculator.multiply(7.0, 6.0), DELTA);
        }
        
        @Test
        @DisplayName("User can multiply any number by zero and get zero")
        void userCanMultiplyByZero() throws CalculatorException {
            assertEquals(0.0, calculator.multiply(0.0, 5.0), DELTA);
            assertEquals(0.0, calculator.multiply(7.0, 0.0), DELTA);
            assertEquals(0.0, calculator.multiply(-3.0, 0.0), DELTA);
            assertEquals(0.0, calculator.multiply(0.0, -8.0), DELTA);
            assertEquals(0.0, calculator.multiply(0.0, 0.0), DELTA);
        }
        
        @Test
        @DisplayName("User can multiply any number by one and get same number")
        void userCanMultiplyByOne() throws CalculatorException {
            assertEquals(7.0, calculator.multiply(7.0, 1.0), DELTA);
            assertEquals(5.0, calculator.multiply(1.0, 5.0), DELTA);
            assertEquals(-3.0, calculator.multiply(-3.0, 1.0), DELTA);
            assertEquals(2.5, calculator.multiply(2.5, 1.0), DELTA);
            assertEquals(-1.5, calculator.multiply(1.0, -1.5), DELTA);
        }
        
        @ParameterizedTest
        @CsvSource({
            "5.0, 3.0, 15.0",
            "-4.0, 2.0, -8.0", 
            "-3.0, -2.0, 6.0",
            "2.5, 4.0, 10.0",
            "0.0, 5.0, 0.0",
            "7.0, 1.0, 7.0"
        })
        @DisplayName("User multiplication scenarios work correctly")
        void userMultiplicationScenarios(double a, double b, double expected) throws CalculatorException {
            assertEquals(expected, calculator.multiply(a, b), DELTA);
        }
    }
}


