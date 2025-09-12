package com.calculator;

/**
 * Exception thrown when invalid input is provided to calculator operations.
 * 
 * @author Developer
 * @version 2.0
 * @since 2.0
 */
public class InvalidInputException extends CalculatorException {
    
    /**
     * Constructs a new InvalidInputException with the specified message.
     * 
     * @param message the detail message explaining the invalid input
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
