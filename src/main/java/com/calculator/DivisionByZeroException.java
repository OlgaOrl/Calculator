package com.calculator;

/**
 * Exception thrown when attempting to divide by zero.
 * 
 * @author Developer
 * @version 2.0
 * @since 2.0
 */
public class DivisionByZeroException extends CalculatorException {
    
    /**
     * Constructs a new DivisionByZeroException with the specified message.
     * 
     * @param message the detail message explaining the division by zero error
     */
    public DivisionByZeroException(String message) {
        super(message);
    }
}

