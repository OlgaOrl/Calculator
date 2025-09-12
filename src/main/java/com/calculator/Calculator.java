package com.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * A comprehensive calculator class providing mathematical operations,
 * memory functionality, and calculation history tracking.
 * 
 * Features include:
 * - Basic arithmetic operations (add, subtract, multiply, divide)
 * - Advanced mathematical operations (power, square root, factorial)
 * - Memory operations (store, recall, clear, add)
 * - Calculation history tracking
 * - Input validation and custom exception handling
 * 
 * @author Developer
 * @version 2.0
 * @since 1.0
 */
public class Calculator {
    
    /** Mathematical constant PI */
    public static final double PI = Math.PI;
    
    /** Mathematical constant E (Euler's number) */
    public static final double E = Math.E;
    
    private double memory;
    private final List<String> history;
    
    /**
     * Constructs a new Calculator instance.
     * Initializes memory to 0 and creates empty calculation history.
     */
    public Calculator() {
        this.memory = 0.0;
        this.history = new ArrayList<>();
    }
    
    // Basic Arithmetic Operations
    
    /**
     * Adds two double numbers and returns their sum.
     * 
     * <p>This method performs basic addition of two double values, handling
     * special cases such as infinity and NaN according to IEEE 754 standard.</p>
     * 
     * <p><strong>Usage Examples:</strong></p>
     * <pre>{@code
     * Calculator calc = new Calculator();
     * double result1 = calc.add(5.0, 3.0);        // returns 8.0
     * double result2 = calc.add(-2.5, 7.5);       // returns 5.0
     * double result3 = calc.add(0.1, 0.2);        // returns 0.3
     * }</pre>
     * 
     * <p><strong>Edge Cases:</strong></p>
     * <ul>
     * <li>Adding with {@code Double.POSITIVE_INFINITY} returns {@code Double.POSITIVE_INFINITY}</li>
     * <li>Adding with {@code Double.NaN} returns {@code Double.NaN}</li>
     * <li>Overflow results in {@code Double.POSITIVE_INFINITY} or {@code Double.NEGATIVE_INFINITY}</li>
     * </ul>
     * 
     * @param a the first number to add
     * @param b the second number to add
     * @return the sum of a and b, following IEEE 754 arithmetic rules
     * @throws InvalidInputException if either parameter is NaN
     * @since 1.1
     */
    public double add(double a, double b) throws InvalidInputException {
        validateInput(a, "First parameter");
        validateInput(b, "Second parameter");
        
        double result = a + b;
        logCalculation(a + " + " + b + " = " + formatResult(result));
        return result;
    }
    
    /**
     * Subtracts the second number from the first number and returns the difference.
     * 
     * <p>This method performs basic subtraction of two double values (a - b), 
     * handling special cases such as infinity and NaN according to IEEE 754 standard.
     * The order of parameters matters: subtract(a, b) returns a - b.</p>
     * 
     * <p><strong>Usage Examples:</strong></p>
     * <pre>{@code
     * Calculator calc = new Calculator();
     * double result1 = calc.subtract(10.0, 3.0);      // returns 7.0
     * double result2 = calc.subtract(5.0, 8.0);       // returns -3.0
     * double result3 = calc.subtract(-2.5, -7.5);     // returns 5.0
     * double result4 = calc.subtract(15.0, 0.0);      // returns 15.0
     * }</pre>
     * 
     * <p><strong>Edge Cases:</strong></p>
     * <ul>
     * <li>Subtracting from {@code Double.POSITIVE_INFINITY} returns {@code Double.POSITIVE_INFINITY}</li>
     * <li>Subtracting {@code Double.POSITIVE_INFINITY} returns {@code Double.NEGATIVE_INFINITY}</li>
     * <li>Subtracting equal infinities: {@code POSITIVE_INFINITY - POSITIVE_INFINITY} returns {@code Double.NaN}</li>
     * <li>Operations with {@code Double.NaN} return {@code Double.NaN}</li>
     * <li>Overflow results in {@code Double.POSITIVE_INFINITY} or {@code Double.NEGATIVE_INFINITY}</li>
     * </ul>
     * 
     * @param a the minuend (number to subtract from)
     * @param b the subtrahend (number to subtract)
     * @return the difference a - b, following IEEE 754 arithmetic rules
     * @throws InvalidInputException if either parameter is NaN
     * @since 1.2
     */
    public double subtract(double a, double b) throws InvalidInputException {
        validateInput(a, "Minuend");
        validateInput(b, "Subtrahend");
        
        double result = a - b;
        logCalculation(a + " - " + b + " = " + formatResult(result));
        return result;
    }
    
    /**
     * Multiplies two numbers and returns their product.
     * 
     * <p>This method allows users to multiply any two numbers, including positive numbers,
     * negative numbers, decimals, and special values like zero and one. The multiplication
     * follows standard mathematical rules and provides clear, accurate results.</p>
     * 
     * <p><strong>User Examples:</strong></p>
     * <pre>{@code
     * Calculator calc = new Calculator();
     * 
     * // Multiply positive numbers
     * double result1 = calc.multiply(5.0, 3.0);        // returns 15.0
     * 
     * // Multiply with negative numbers  
     * double result2 = calc.multiply(-4.0, 2.0);       // returns -8.0
     * double result3 = calc.multiply(-3.0, -2.0);      // returns 6.0
     * 
     * // Multiply decimals
     * double result4 = calc.multiply(2.5, 4.0);        // returns 10.0
     * 
     * // Multiply by zero (always gives zero)
     * double result5 = calc.multiply(0.0, 5.0);        // returns 0.0
     * 
     * // Multiply by one (gives same number) 
     * double result6 = calc.multiply(7.0, 1.0);        // returns 7.0
     * }</pre>
     * 
     * <p><strong>Mathematical Properties:</strong></p>
     * <ul>
     * <li>Commutative: a × b = b × a</li>
     * <li>Identity: a × 1 = a</li>
     * <li>Zero property: a × 0 = 0</li>
     * <li>Sign rules: (-) × (-) = (+), (-) × (+) = (-), (+) × (+) = (+)</li>
     * </ul>
     * 
     * @param a the first number to multiply
     * @param b the second number to multiply  
     * @return the product of a and b
     * @throws InvalidInputException if either parameter is NaN
     * @since 1.3
     */
    public double multiply(double a, double b) throws InvalidInputException {
        validateInput(a, "Multiplicand");
        validateInput(b, "Multiplier");
        
        double result = a * b;
        logCalculation(a + " * " + b + " = " + formatResult(result));
        return result;
    }
    
    /**
     * Divides the first number by the second.
     * 
     * @param a the dividend
     * @param b the divisor
     * @return the quotient of a divided by b
     * @throws DivisionByZeroException if b is zero
     * @throws InvalidInputException if either parameter is NaN
     */
    public double divide(double a, double b) throws DivisionByZeroException, InvalidInputException {
        validateInput(a, "Dividend");
        validateInput(b, "Divisor");
        
        if (b == 0.0) {
            throw new DivisionByZeroException("Cannot divide " + a + " by zero");
        }
        
        double result = a / b;
        logCalculation(a + " / " + b + " = " + formatResult(result));
        return result;
    }
    
    // Advanced Mathematical Operations
    
    /**
     * Raises the base to the power of the exponent.
     * 
     * @param base the base number
     * @param exponent the exponent
     * @return base raised to the power of exponent
     * @throws InvalidInputException if either parameter is NaN
     */
    public double power(double base, double exponent) throws InvalidInputException {
        validateInput(base, "Base");
        validateInput(exponent, "Exponent");
        
        double result = Math.pow(base, exponent);
        logCalculation(base + " ^ " + exponent + " = " + formatResult(result));
        return result;
    }
    
    /**
     * Calculates the square root of a number.
     * 
     * @param number the number to find square root of
     * @return the square root of the number
     * @throws InvalidInputException if number is negative or NaN
     */
    public double squareRoot(double number) throws InvalidInputException {
        validateInput(number, "Number");
        if (number < 0) {
            throw new InvalidInputException("Cannot calculate square root of negative number: " + number);
        }
        
        double result = Math.sqrt(number);
        logCalculation("√" + number + " = " + formatResult(result));
        return result;
    }
    
    /**
     * Calculates percentage of a number.
     * 
     * @param number the base number
     * @param percent the percentage
     * @return the percentage of the number
     * @throws InvalidInputException if either parameter is NaN
     */
    public double percentage(double number, double percent) throws InvalidInputException {
        validateInput(number, "Number");
        validateInput(percent, "Percent");
        
        double result = (number * percent) / 100.0;
        logCalculation(percent + "% of " + number + " = " + formatResult(result));
        return result;
    }
    
    /**
     * Returns the absolute value of a number.
     * 
     * @param number the number
     * @return the absolute value
     * @throws InvalidInputException if parameter is NaN
     */
    public double absolute(double number) throws InvalidInputException {
        validateInput(number, "Number");
        
        double result = Math.abs(number);
        logCalculation("|" + number + "| = " + formatResult(result));
        return result;
    }
    
    /**
     * Calculates factorial of a non-negative integer.
     * 
     * @param number the number to calculate factorial for
     * @return the factorial of the number
     * @throws InvalidInputException if number is negative or not an integer
     */
    public long factorial(int number) throws InvalidInputException {
        if (number < 0) {
            throw new InvalidInputException("Factorial is not defined for negative numbers: " + number);
        }
        
        if (number > 20) {
            throw new InvalidInputException("Factorial calculation would overflow for number: " + number);
        }
        
        long result = 1;
        for (int i = 2; i <= number; i++) {
            result *= i;
        }
        
        logCalculation(number + "! = " + result);
        return result;
    }
    
    // Memory Operations
    
    /**
     * Stores a value in memory.
     * 
     * @param value the value to store
     * @throws InvalidInputException if value is NaN
     */
    public void memoryStore(double value) throws InvalidInputException {
        validateInput(value, "Memory value");
        this.memory = value;
    }
    
    /**
     * Recalls the value from memory.
     * 
     * @return the stored memory value
     */
    public double memoryRecall() {
        return memory;
    }
    
    /**
     * Clears the memory (sets to 0).
     */
    public void memoryClear() {
        this.memory = 0.0;
    }
    
    /**
     * Adds a value to the current memory value.
     * 
     * @param value the value to add to memory
     * @throws InvalidInputException if value is NaN
     */
    public void memoryAdd(double value) throws InvalidInputException {
        validateInput(value, "Memory add value");
        this.memory += value;
    }
    
    // History Operations
    
    /**
     * Returns a copy of the calculation history.
     * 
     * @return list of calculation history entries
     */
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }
    
    /**
     * Clears the calculation history.
     */
    public void clearHistory() {
        history.clear();
    }
    
    /**
     * Returns the last calculation from history.
     * 
     * @return the last calculation or null if history is empty
     */
    public String getLastCalculation() {
        return history.isEmpty() ? null : history.get(history.size() - 1);
    }
    
    // Utility Methods
    
    /**
     * Rounds a value to specified decimal places.
     * 
     * @param value the value to round
     * @param places the number of decimal places
     * @return the rounded value
     * @throws InvalidInputException if places is negative
     */
    public double round(double value, int places) throws InvalidInputException {
        if (places < 0) {
            throw new InvalidInputException("Decimal places cannot be negative: " + places);
        }
        
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    /**
     * Checks if a double value represents an integer.
     * 
     * @param value the value to check
     * @return true if value is an integer
     */
    public boolean isInteger(double value) {
        return value == Math.floor(value) && !Double.isInfinite(value);
    }
    
    /**
     * Formats a result for display.
     * 
     * @param result the result to format
     * @return formatted string representation
     */
    public String formatResult(double result) {
        if (Double.isNaN(result)) {
            return "NaN";
        } else if (Double.isInfinite(result)) {
            return result > 0 ? "∞" : "-∞";
        } else if (isInteger(result)) {
            return String.valueOf((long) result);
        } else {
            return String.valueOf(result);
        }
    }
    
    // Private Helper Methods
    
    private void validateInput(double value, String parameterName) throws InvalidInputException {
        if (Double.isNaN(value)) {
            throw new InvalidInputException(parameterName + " cannot be NaN");
        }
    }
    
    private void logCalculation(String calculation) {
        history.add(calculation);
    }
    
    @Override
    public String toString() {
        return "Calculator[ready for operations, memory=" + formatResult(memory) + 
               ", history entries=" + history.size() + "]";
    }
}








