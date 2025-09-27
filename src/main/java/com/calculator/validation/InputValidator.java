package com.calculator.validation;

import com.calculator.CalculatorConfig;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Comprehensive input validation utility for the Calculator application.
 * 
 * This class implements the Strategy pattern for different validation rules
 * and provides static methods for validating various types of calculator inputs.
 * It supports number format validation, range checking, operation validation,
 * and expression syntax validation with detailed error reporting.
 * 
 * Features:
 * - Number format validation with scientific notation support
 * - Range checking for safe mathematical operations
 * - Operation symbol validation
 * - Expression syntax validation
 * - Input sanitization methods
 * - Overflow/underflow detection
 * - Configurable validation rules
 * 
 * @author Calculator Development Team
 * @version 1.0
 * @since 2.0
 */
public class InputValidator {
    
    // Regular expression patterns for validation
    private static final Pattern NUMBER_PATTERN = Pattern.compile(
        "^[+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?$"
    );
    
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^[+-]?\\d+$");
    
    private static final Pattern SCIENTIFIC_NOTATION_PATTERN = Pattern.compile(
        "^[+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)[eE][+-]?\\d+$"
    );
    
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(
        "^[+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?(?:\\s*[+\\-×÷*/]\\s*[+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)*$"
    );
    
    // Valid operation symbols
    private static final Set<String> VALID_OPERATIONS = new HashSet<>(Arrays.asList(
        "+", "-", "×", "÷", "*", "/", "^", "√", "∛", "%", "!", "sin", "cos", "tan", "log", "ln"
    ));
    
    // Mathematical constants
    private static final double MAX_SAFE_INTEGER = 9007199254740991.0; // 2^53 - 1
    private static final double MIN_SAFE_INTEGER = -9007199254740991.0; // -(2^53 - 1)
    
    /**
     * Validation strategy interface for implementing different validation rules.
     */
    @FunctionalInterface
    public interface ValidationStrategy {
        void validate(String input) throws ValidationException;
    }
    
    /**
     * Validates a string input as a valid number.
     * 
     * @param input the string to validate as a number
     * @return the parsed double value
     * @throws ValidationException if the input is not a valid number
     */
    public static double validateNumber(String input) throws ValidationException {
        if (input == null) {
            throw new ValidationException("Input cannot be null", input, "NULL_INPUT");
        }
        
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            throw new ValidationException("Input cannot be empty", input, "EMPTY_INPUT");
        }
        
        // Check for invalid characters
        if (!NUMBER_PATTERN.matcher(trimmedInput).matches()) {
            throw new ValidationException(
                "Invalid number format: '" + input + "'. Expected format: [+-]?digits[.digits][e[+-]digits]",
                input, "INVALID_NUMBER_FORMAT"
            );
        }
        
        try {
            double value = Double.parseDouble(trimmedInput);
            
            // Check for special values
            if (Double.isNaN(value)) {
                throw new ValidationException("Input results in NaN (Not a Number)", input, "NAN_VALUE");
            }
            
            if (Double.isInfinite(value)) {
                throw new ValidationException("Input results in infinite value", input, "INFINITE_VALUE");
            }
            
            // Check for underflow (too close to zero)
            if (value != 0.0 && Math.abs(value) < Double.MIN_NORMAL) {
                throw new ValidationException(
                    "Number is too small (underflow): " + value,
                    input, "UNDERFLOW"
                );
            }
            
            return value;
            
        } catch (NumberFormatException e) {
            throw new ValidationException(
                "Cannot parse '" + input + "' as a valid number",
                input, "PARSE_ERROR", e
            );
        }
    }
    
    /**
     * Validates a string input as a valid integer.
     * 
     * @param input the string to validate as an integer
     * @return the parsed integer value
     * @throws ValidationException if the input is not a valid integer
     */
    public static int validateInteger(String input) throws ValidationException {
        if (input == null || input.trim().isEmpty()) {
            throw new ValidationException("Integer input cannot be null or empty", input, "NULL_OR_EMPTY");
        }
        
        String trimmedInput = input.trim();
        
        if (!INTEGER_PATTERN.matcher(trimmedInput).matches()) {
            throw new ValidationException(
                "Invalid integer format: '" + input + "'. Expected format: [+-]?digits",
                input, "INVALID_INTEGER_FORMAT"
            );
        }
        
        try {
            long longValue = Long.parseLong(trimmedInput);
            
            if (longValue > Integer.MAX_VALUE || longValue < Integer.MIN_VALUE) {
                throw new ValidationException(
                    "Integer value out of range: " + longValue + " (range: " + 
                    Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ")",
                    input, "INTEGER_OVERFLOW"
                );
            }
            
            return (int) longValue;
            
        } catch (NumberFormatException e) {
            throw new ValidationException(
                "Cannot parse '" + input + "' as a valid integer",
                input, "INTEGER_PARSE_ERROR", e
            );
        }
    }
    
    /**
     * Validates an operation symbol.
     * 
     * @param operation the operation symbol to validate
     * @throws ValidationException if the operation is not valid
     */
    public static void validateOperation(String operation) throws ValidationException {
        if (operation == null) {
            throw new ValidationException("Operation cannot be null", operation, "NULL_OPERATION");
        }
        
        String trimmedOperation = operation.trim();
        if (trimmedOperation.isEmpty()) {
            throw new ValidationException("Operation cannot be empty", operation, "EMPTY_OPERATION");
        }
        
        if (!VALID_OPERATIONS.contains(trimmedOperation)) {
            throw new ValidationException(
                "Invalid operation: '" + operation + "'. Valid operations: " + VALID_OPERATIONS,
                operation, "INVALID_OPERATION"
            );
        }
    }
    
    /**
     * Validates a mathematical expression syntax.
     * 
     * @param expression the expression to validate
     * @throws ValidationException if the expression syntax is invalid
     */
    public static void validateExpression(String expression) throws ValidationException {
        if (expression == null) {
            throw new ValidationException("Expression cannot be null", expression, "NULL_EXPRESSION");
        }
        
        String trimmedExpression = expression.trim();
        if (trimmedExpression.isEmpty()) {
            throw new ValidationException("Expression cannot be empty", expression, "EMPTY_EXPRESSION");
        }
        
        // Check for balanced parentheses
        if (!hasBalancedParentheses(trimmedExpression)) {
            throw new ValidationException(
                "Unbalanced parentheses in expression: '" + expression + "'",
                expression, "UNBALANCED_PARENTHESES"
            );
        }
        
        // Check for consecutive operators
        if (hasConsecutiveOperators(trimmedExpression)) {
            throw new ValidationException(
                "Consecutive operators found in expression: '" + expression + "'",
                expression, "CONSECUTIVE_OPERATORS"
            );
        }
        
        // Check for valid expression pattern
        if (!EXPRESSION_PATTERN.matcher(trimmedExpression).matches()) {
            throw new ValidationException(
                "Invalid expression syntax: '" + expression + "'",
                expression, "INVALID_EXPRESSION_SYNTAX"
            );
        }
    }
    
    /**
     * Validates if a number is within the specified range.
     * 
     * @param number the number to check
     * @param min the minimum allowed value (inclusive)
     * @param max the maximum allowed value (inclusive)
     * @return true if the number is within range
     * @throws ValidationException if the number is outside the valid range
     */
    public static boolean isValidRange(double number, double min, double max) throws ValidationException {
        if (Double.isNaN(number)) {
            throw new ValidationException("Cannot validate range for NaN value", String.valueOf(number), "NAN_RANGE_CHECK");
        }
        
        if (Double.isInfinite(number)) {
            throw new ValidationException("Cannot validate range for infinite value", String.valueOf(number), "INFINITE_RANGE_CHECK");
        }
        
        if (min > max) {
            throw new ValidationException(
                "Invalid range: minimum (" + min + ") is greater than maximum (" + max + ")",
                String.format("min=%.2f, max=%.2f", min, max), "INVALID_RANGE"
            );
        }
        
        if (number < min || number > max) {
            throw new ValidationException(
                String.format("Number %.6f is outside valid range [%.6f, %.6f]", number, min, max),
                String.valueOf(number), "OUT_OF_RANGE"
            );
        }
        
        return true;
    }
    
    /**
     * Validates if a number is safe for mathematical operations.
     * 
     * @param number the number to validate
     * @throws ValidationException if the number is unsafe for calculations
     */
    public static void validateSafeNumber(double number) throws ValidationException {
        if (Double.isNaN(number)) {
            throw new ValidationException("Number is NaN (Not a Number)", String.valueOf(number), "NAN_UNSAFE");
        }
        
        if (Double.isInfinite(number)) {
            throw new ValidationException("Number is infinite", String.valueOf(number), "INFINITE_UNSAFE");
        }
        
        // Check for numbers that might cause overflow in calculations
        if (Math.abs(number) > MAX_SAFE_INTEGER) {
            throw new ValidationException(
                "Number is too large for safe calculations: " + number + " (max safe: " + MAX_SAFE_INTEGER + ")",
                String.valueOf(number), "UNSAFE_LARGE_NUMBER"
            );
        }
        
        // Check for subnormal numbers that might cause precision issues
        if (number != 0.0 && Math.abs(number) < Double.MIN_NORMAL) {
            throw new ValidationException(
                "Number is too small for reliable calculations: " + number,
                String.valueOf(number), "UNSAFE_SMALL_NUMBER"
            );
        }
    }
    
    /**
     * Validates scientific notation format.
     * 
     * @param input the input to validate as scientific notation
     * @return the parsed double value
     * @throws ValidationException if the input is not valid scientific notation
     */
    public static double validateScientificNotation(String input) throws ValidationException {
        if (input == null || input.trim().isEmpty()) {
            throw new ValidationException("Scientific notation input cannot be null or empty", input, "NULL_OR_EMPTY_SCIENTIFIC");
        }
        
        String trimmedInput = input.trim();
        
        if (!SCIENTIFIC_NOTATION_PATTERN.matcher(trimmedInput).matches()) {
            throw new ValidationException(
                "Invalid scientific notation format: '" + input + "'. Expected format: [+-]?digits[.digits]e[+-]digits",
                input, "INVALID_SCIENTIFIC_FORMAT"
            );
        }
        
        try {
            double value = Double.parseDouble(trimmedInput);
            validateSafeNumber(value);
            return value;
            
        } catch (NumberFormatException e) {
            throw new ValidationException(
                "Cannot parse scientific notation: '" + input + "'",
                input, "SCIENTIFIC_PARSE_ERROR", e
            );
        }
    }
    
    /**
     * Sanitizes input by removing invalid characters and normalizing format.
     * 
     * @param input the input to sanitize
     * @return sanitized input string
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        
        // Remove leading/trailing whitespace
        String sanitized = input.trim();
        
        // Replace common alternative symbols
        sanitized = sanitized.replace("×", "*")
                            .replace("÷", "/")
                            .replace("−", "-"); // Unicode minus to ASCII minus
        
        // Remove multiple consecutive spaces
        sanitized = sanitized.replaceAll("\\s+", " ");
        
        // Remove invalid characters (keep only digits, operators, parentheses, decimal point, e/E)
        sanitized = sanitized.replaceAll("[^0-9+\\-*/().eE\\s]", "");
        
        return sanitized;
    }
    
    /**
     * Validates input using configuration-based rules.
     * 
     * @param input the input to validate
     * @param config the calculator configuration
     * @throws ValidationException if validation fails
     */
    public static void validateWithConfig(String input, CalculatorConfig config) throws ValidationException {
        if (!config.isValidationEnabled()) {
            return; // Skip validation if disabled
        }
        
        double number = validateNumber(input);
        
        // Apply configuration-based range validation
        double minValue = config.getMinNumberValue();
        double maxValue = config.getMaxNumberValue();
        isValidRange(number, minValue, maxValue);
        
        // Apply strict mode validation if enabled
        if (config.isStrictModeEnabled()) {
            validateStrictMode(input, number);
        }
    }
    
    /**
     * Applies strict mode validation rules.
     * 
     * @param input the original input string
     * @param number the parsed number value
     * @throws ValidationException if strict validation fails
     */
    private static void validateStrictMode(String input, double number) throws ValidationException {
        // In strict mode, reject numbers with excessive precision
        String numberStr = String.valueOf(number);
        if (numberStr.length() > 15) {
            throw new ValidationException(
                "Number has excessive precision in strict mode: '" + input + "'",
                input, "EXCESSIVE_PRECISION"
            );
        }
        
        // Reject very small numbers that might cause precision issues
        if (number != 0.0 && Math.abs(number) < 1e-10) {
            throw new ValidationException(
                "Number is too small for strict mode: " + number,
                input, "STRICT_MODE_TOO_SMALL"
            );
        }
        
        // Reject very large numbers that might cause overflow
        if (Math.abs(number) > 1e10) {
            throw new ValidationException(
                "Number is too large for strict mode: " + number,
                input, "STRICT_MODE_TOO_LARGE"
            );
        }
    }
    
    /**
     * Validates division operation to prevent division by zero.
     * 
     * @param dividend the dividend
     * @param divisor the divisor
     * @throws ValidationException if division by zero is attempted
     */
    public static void validateDivision(double dividend, double divisor) throws ValidationException {
        validateSafeNumber(dividend);
        validateSafeNumber(divisor);
        
        if (divisor == 0.0) {
            throw new ValidationException(
                "Division by zero: " + dividend + " ÷ 0",
                String.valueOf(divisor), "DIVISION_BY_ZERO"
            );
        }
        
        // Check for potential overflow in division
        if (Math.abs(dividend) > Double.MAX_VALUE * Math.abs(divisor)) {
            throw new ValidationException(
                "Division would cause overflow: " + dividend + " ÷ " + divisor,
                dividend + "/" + divisor, "DIVISION_OVERFLOW"
            );
        }
    }
    
    /**
     * Validates factorial operation input.
     * 
     * @param n the number for factorial calculation
     * @throws ValidationException if the input is invalid for factorial
     */
    public static void validateFactorial(int n) throws ValidationException {
        if (n < 0) {
            throw new ValidationException(
                "Factorial is not defined for negative numbers: " + n,
                String.valueOf(n), "NEGATIVE_FACTORIAL"
            );
        }
        
        if (n > 170) {
            throw new ValidationException(
                "Factorial would cause overflow for number: " + n + " (maximum: 170)",
                String.valueOf(n), "FACTORIAL_OVERFLOW"
            );
        }
    }
    
    /**
     * Validates square root operation input.
     * 
     * @param number the number for square root calculation
     * @throws ValidationException if the input is invalid for square root
     */
    public static void validateSquareRoot(double number) throws ValidationException {
        validateSafeNumber(number);
        
        if (number < 0) {
            throw new ValidationException(
                "Square root is not defined for negative numbers in real domain: " + number,
                String.valueOf(number), "NEGATIVE_SQUARE_ROOT"
            );
        }
    }
    
    /**
     * Validates logarithm operation input.
     * 
     * @param number the number for logarithm calculation
     * @throws ValidationException if the input is invalid for logarithm
     */
    public static void validateLogarithm(double number) throws ValidationException {
        validateSafeNumber(number);
        
        if (number <= 0) {
            throw new ValidationException(
                "Logarithm is not defined for non-positive numbers: " + number,
                String.valueOf(number), "NON_POSITIVE_LOGARITHM"
            );
        }
    }
    
    // Helper methods
    
    /**
     * Checks if an expression has balanced parentheses.
     * 
     * @param expression the expression to check
     * @return true if parentheses are balanced
     */
    private static boolean hasBalancedParentheses(String expression) {
        int count = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
                if (count < 0) {
                    return false;
                }
            }
        }
        return count == 0;
    }
    
    /**
     * Checks if an expression has consecutive operators.
     * 
     * @param expression the expression to check
     * @return true if consecutive operators are found
     */
    private static boolean hasConsecutiveOperators(String expression) {
        Pattern consecutiveOps = Pattern.compile("[+\\-*/]{2,}");
        return consecutiveOps.matcher(expression).find();
    }
}