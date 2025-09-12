
package com.calculator;

/**
 * Base exception class for all calculator-related exceptions.
 * 
 * @author Developer
 * @version 2.0
 * @since 2.0
 */
public class CalculatorException extends Exception {
    
    /**
     * Constructs a new CalculatorException with the specified message.
     * 
     * @param message the detail message explaining the exception
     */
    public CalculatorException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new CalculatorException with the specified message and cause.
     * 
     * @param message the detail message explaining the exception
     * @param cause the cause of this exception
     */
    public CalculatorException(String message, Throwable cause) {
        super(message, cause);
    }
}

