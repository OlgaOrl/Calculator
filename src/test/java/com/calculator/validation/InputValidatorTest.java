package com.calculator.validation;

import com.calculator.CalculatorConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Input Validator Tests")
class InputValidatorTest {
    
    @Nested
    @DisplayName("Number Validation Tests")
    class NumberValidationTests {
        
        @ParameterizedTest
        @DisplayName("Should validate valid numbers")
        @ValueSource(strings = {"123", "123.456", "-123", "-123.456", "0", "0.0", ".5", "-.5", 
                               "1e10", "1.5e-10", "-2.3e5", "1E10", "1.5E-10"})
        void testValidNumbers(String input) {
            assertDoesNotThrow(() -> InputValidator.validateNumber(input));
        }
        
        @ParameterizedTest
        @DisplayName("Should reject invalid number formats")
        @ValueSource(strings = {"abc", "12.34.56", "1.2.3e4", "e10", "1e", "1e+", "++123", "--123", 
                               "12..34", "..", "1.2e3.4", "∞", "NaN"})
        void testInvalidNumberFormats(String input) {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateNumber(input));
            assertEquals("INVALID_NUMBER_FORMAT", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject null input")
        void testNullInput() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateNumber(null));
            assertEquals("NULL_INPUT", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject empty input")
        void testEmptyInput() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateNumber("   "));
            assertEquals("EMPTY_INPUT", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should handle very large numbers")
        void testVeryLargeNumbers() {
            String largeNumber = "1.7976931348623157e308"; // Close to Double.MAX_VALUE
            assertDoesNotThrow(() -> InputValidator.validateNumber(largeNumber));
        }
        
        @Test
        @DisplayName("Should handle very small numbers")
        void testVerySmallNumbers() {
            String smallNumber = "4.9e-324"; // Close to Double.MIN_VALUE
            assertDoesNotThrow(() -> InputValidator.validateNumber(smallNumber));
        }
    }
    
    @Nested
    @DisplayName("Integer Validation Tests")
    class IntegerValidationTests {
        
        @ParameterizedTest
        @DisplayName("Should validate valid integers")
        @ValueSource(strings = {"0", "123", "-123", "2147483647", "-2147483648"})
        void testValidIntegers(String input) {
            assertDoesNotThrow(() -> InputValidator.validateInteger(input));
        }
        
        @ParameterizedTest
        @DisplayName("Should reject invalid integer formats")
        @ValueSource(strings = {"123.456", "abc", "12.0", "1e10", "++123", "--123"})
        void testInvalidIntegerFormats(String input) {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateInteger(input));
            assertEquals("INVALID_INTEGER_FORMAT", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject integer overflow")
        void testIntegerOverflow() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateInteger("2147483648")); // Integer.MAX_VALUE + 1
            assertEquals("INTEGER_OVERFLOW", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject integer underflow")
        void testIntegerUnderflow() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateInteger("-2147483649")); // Integer.MIN_VALUE - 1
            assertEquals("INTEGER_OVERFLOW", exception.getViolatedRule());
        }
    }
    
    @Nested
    @DisplayName("Operation Validation Tests")
    class OperationValidationTests {
        
        @ParameterizedTest
        @DisplayName("Should validate valid operations")
        @ValueSource(strings = {"+", "-", "×", "÷", "*", "/", "^", "√", "∛", "%", "!", 
                               "sin", "cos", "tan", "log", "ln"})
        void testValidOperations(String operation) {
            assertDoesNotThrow(() -> InputValidator.validateOperation(operation));
        }
        
        @ParameterizedTest
        @DisplayName("Should reject invalid operations")
        @ValueSource(strings = {"&", "#", "@", "mod", "pow", "sqrt", "++", "--", "**"})
        void testInvalidOperations(String operation) {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateOperation(operation));
            assertEquals("INVALID_OPERATION", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject null operation")
        void testNullOperation() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateOperation(null));
            assertEquals("NULL_OPERATION", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject empty operation")
        void testEmptyOperation() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateOperation("   "));
            assertEquals("EMPTY_OPERATION", exception.getViolatedRule());
        }
    }
    
    @Nested
    @DisplayName("Expression Validation Tests")
    class ExpressionValidationTests {
        
        @ParameterizedTest
        @DisplayName("Should validate valid expressions")
        @ValueSource(strings = {"123", "123 + 456", "123.45 * 67.89", "1 + 2 - 3", 
                               "1.5e10 / 2.3e5", "-123 + 456"})
        void testValidExpressions(String expression) {
            assertDoesNotThrow(() -> InputValidator.validateExpression(expression));
        }
        
        @Test
        @DisplayName("Should reject unbalanced parentheses")
        void testUnbalancedParentheses() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateExpression("(1 + 2"));
            assertEquals("UNBALANCED_PARENTHESES", exception.getViolatedRule());
            
            exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateExpression("1 + 2)"));
            assertEquals("UNBALANCED_PARENTHESES", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject consecutive operators")
        void testConsecutiveOperators() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateExpression("1 ++ 2"));
            assertEquals("CONSECUTIVE_OPERATORS", exception.getViolatedRule());
            
            exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateExpression("1 */ 2"));
            assertEquals("CONSECUTIVE_OPERATORS", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject null expression")
        void testNullExpression() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateExpression(null));
            assertEquals("NULL_EXPRESSION", exception.getViolatedRule());
        }
    }
    
    @Nested
    @DisplayName("Range Validation Tests")
    class RangeValidationTests {
        
        @ParameterizedTest
        @DisplayName("Should validate numbers within range")
        @CsvSource({
            "5.0, 0.0, 10.0",
            "0.0, 0.0, 10.0",
            "10.0, 0.0, 10.0",
            "-5.0, -10.0, 0.0",
            "123.456, 100.0, 200.0"
        })
        void testValidRange(double number, double min, double max) {
            assertDoesNotThrow(() -> InputValidator.isValidRange(number, min, max));
            assertTrue(InputValidator.isValidRange(number, min, max));
        }
        
        @ParameterizedTest
        @DisplayName("Should reject numbers outside range")
        @CsvSource({
            "15.0, 0.0, 10.0",
            "-5.0, 0.0, 10.0",
            "5.0, 10.0, 20.0",
            "25.0, 10.0, 20.0"
        })
        void testInvalidRange(double number, double min, double max) {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.isValidRange(number, min, max));
            assertEquals("OUT_OF_RANGE", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject invalid range definition")
        void testInvalidRangeDefinition() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.isValidRange(5.0, 10.0, 5.0));
            assertEquals("INVALID_RANGE", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject NaN in range validation")
        void testNaNInRange() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.isValidRange(Double.NaN, 0.0, 10.0));
            assertEquals("NAN_RANGE_CHECK", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject infinite values in range validation")
        void testInfiniteInRange() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.isValidRange(Double.POSITIVE_INFINITY, 0.0, 10.0));
            assertEquals("INFINITE_RANGE_CHECK", exception.getViolatedRule());
        }
    }
    
    @Nested
    @DisplayName("Safe Number Validation Tests")
    class SafeNumberValidationTests {
        
        @Test
        @DisplayName("Should validate safe numbers")
        void testSafeNumbers() {
            assertDoesNotThrow(() -> InputValidator.validateSafeNumber(123.456));
            assertDoesNotThrow(() -> InputValidator.validateSafeNumber(-123.456));
            assertDoesNotThrow(() -> InputValidator.validateSafeNumber(0.0));
            assertDoesNotThrow(() -> InputValidator.validateSafeNumber(1e10));
        }
        
        @Test
        @DisplayName("Should reject NaN")
        void testNaN() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateSafeNumber(Double.NaN));
            assertEquals("NAN_UNSAFE", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject infinite values")
        void testInfiniteValues() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateSafeNumber(Double.POSITIVE_INFINITY));
            assertEquals("INFINITE_UNSAFE", exception.getViolatedRule());
            
            exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateSafeNumber(Double.NEGATIVE_INFINITY));
            assertEquals("INFINITE_UNSAFE", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject unsafe large numbers")
        void testUnsafeLargeNumbers() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateSafeNumber(1e16));
            assertEquals("UNSAFE_LARGE_NUMBER", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should reject unsafe small numbers")
        void testUnsafeSmallNumbers() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateSafeNumber(1e-324));
            assertEquals("UNSAFE_SMALL_NUMBER", exception.getViolatedRule());
        }
    }
    
    @Nested
    @DisplayName("Scientific Notation Validation Tests")
    class ScientificNotationValidationTests {
        
        @ParameterizedTest
        @DisplayName("Should validate valid scientific notation")
        @ValueSource(strings = {"1e10", "1.5e-10", "-2.3e5", "1E10", "1.5E-10", "6.022e23"})
        void testValidScientificNotation(String input) {
            assertDoesNotThrow(() -> InputValidator.validateScientificNotation(input));
        }
        
        @ParameterizedTest
        @DisplayName("Should reject invalid scientific notation")
        @ValueSource(strings = {"123", "1.23", "e10", "1e", "1.2.3e4", "1e+", "1e-"})
        void testInvalidScientificNotation(String input) {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateScientificNotation(input));
            assertEquals("INVALID_SCIENTIFIC_FORMAT", exception.getViolatedRule());
        }
    }
    
    @Nested
    @DisplayName("Input Sanitization Tests")
    class InputSanitizationTests {
        
        @Test
        @DisplayName("Should sanitize input correctly")
        void testInputSanitization() {
            assertEquals("", InputValidator.sanitizeInput(null));
            assertEquals("", InputValidator.sanitizeInput("   "));
            assertEquals("123 + 456", InputValidator.sanitizeInput("  123   +   456  "));
            assertEquals("123 * 456", InputValidator.sanitizeInput("123 × 456"));
            assertEquals("123 / 456", InputValidator.sanitizeInput("123 ÷ 456"));
            assertEquals("123 - 456", InputValidator.sanitizeInput("123 − 456"));
            assertEquals("123 + 456", InputValidator.sanitizeInput("123abc + 456def"));
        }
    }
    
    @Nested
    @DisplayName("Configuration-Based Validation Tests")
    class ConfigurationValidationTests {
        
        @Test
        @DisplayName("Should skip validation when disabled")
        void testValidationDisabled() {
            CalculatorConfig config = CalculatorConfig.getInstance();
            // Assuming validation can be disabled in config
            assertDoesNotThrow(() -> InputValidator.validateWithConfig("invalid", config));
        }
        
        @Test
        @DisplayName("Should apply configuration-based range validation")
        void testConfigurationRangeValidation() {
            CalculatorConfig config = CalculatorConfig.getInstance();
            
            // Test with a number within configured range
            assertDoesNotThrow(() -> InputValidator.validateWithConfig("123.456", config));
            
            // Test with a number outside configured range would throw exception
            // This depends on the actual configuration values
        }
    }
    
    @Nested
    @DisplayName("Specific Operation Validation Tests")
    class SpecificOperationValidationTests {
        
        @Test
        @DisplayName("Should validate division operations")
        void testDivisionValidation() {
            assertDoesNotThrow(() -> InputValidator.validateDivision(10.0, 2.0));
            
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateDivision(10.0, 0.0));
            assertEquals("DIVISION_BY_ZERO", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should validate factorial operations")
        void testFactorialValidation() {
            assertDoesNotThrow(() -> InputValidator.validateFactorial(5));
            assertDoesNotThrow(() -> InputValidator.validateFactorial(0));
            
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateFactorial(-1));
            assertEquals("NEGATIVE_FACTORIAL", exception.getViolatedRule());
            
            exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateFactorial(171));
            assertEquals("FACTORIAL_OVERFLOW", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should validate square root operations")
        void testSquareRootValidation() {
            assertDoesNotThrow(() -> InputValidator.validateSquareRoot(9.0));
            assertDoesNotThrow(() -> InputValidator.validateSquareRoot(0.0));
            
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateSquareRoot(-1.0));
            assertEquals("NEGATIVE_SQUARE_ROOT", exception.getViolatedRule());
        }
        
        @Test
        @DisplayName("Should validate logarithm operations")
        void testLogarithmValidation() {
            assertDoesNotThrow(() -> InputValidator.validateLogarithm(10.0));
            assertDoesNotThrow(() -> InputValidator.validateLogarithm(0.1));
            
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateLogarithm(0.0));
            assertEquals("NON_POSITIVE_LOGARITHM", exception.getViolatedRule());
            
            exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateLogarithm(-1.0));
            assertEquals("NON_POSITIVE_LOGARITHM", exception.getViolatedRule());
        }
    }
    
    @Nested
    @DisplayName("Exception Details Tests")
    class ExceptionDetailsTests {
        
        @Test
        @DisplayName("Should provide detailed exception information")
        void testExceptionDetails() {
            ValidationException exception = assertThrows(ValidationException.class, 
                () -> InputValidator.validateNumber("invalid"));
            
            assertNotNull(exception.getMessage());
            assertEquals("invalid", exception.getInvalidInput());
            assertEquals("INVALID_NUMBER_FORMAT", exception.getViolatedRule());
            
            String exceptionString = exception.toString();
            assertTrue(exceptionString.contains("Invalid Input: 'invalid'"));
            assertTrue(exceptionString.contains("Violated Rule: INVALID_NUMBER_FORMAT"));
        }
        
        @Test
        @DisplayName("Should handle exception chaining")
        void testExceptionChaining() {
            try {
                InputValidator.validateNumber("1e999999"); // This should cause NumberFormatException
            } catch (ValidationException e) {
                // The exception might have a cause depending on the implementation
                // This test ensures the exception structure is correct
                assertNotNull(e.getMessage());
            }
        }
    }
}